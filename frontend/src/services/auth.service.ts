import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { JwtHelperService } from "@auth0/angular-jwt";
import { API_CONFIG } from "../config/api.config";
import { CredenciaisDTO } from "../models/credenciais.dto";
import { LocalUser } from "../models/local_user";
import { StorageService } from "./storage.service";

@Injectable()
export class AuthService {

    jwtHelperService: JwtHelperService = new JwtHelperService();

    constructor(public http: HttpClient, public storage: StorageService){

    }

    authenticate(creds: CredenciaisDTO){
        return this.http.post(
            `${API_CONFIG.baseUrl}/login`, 
            creds,
            {
                observe: 'response',
                responseType: 'text'
            });
    }

    successfulLogin(authorizationValue: string){
        let tok = authorizationValue.substring(7);
        console.log(this.jwtHelperService.decodeToken(tok))
        let user : LocalUser = {
            token: tok,
            email: this.jwtHelperService.decodeToken(tok).sub
        };
        this.storage.setLocalUser(user);
    }

    logOut(){
        this.storage.setLocalUser(null);
    }

}