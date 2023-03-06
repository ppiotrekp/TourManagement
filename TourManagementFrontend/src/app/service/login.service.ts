import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {StorageService} from "./storage.service";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  email = '';
  password = '';
  private header = new HttpHeaders();
  private refreshToken: any;
  loginUrl = 'http://localhost:8080/login';
  refreshTokenUrl = 'http://localhost:8080/refresh-token'

  constructor(private httpClient: HttpClient, private storageService: StorageService) { }

  login(payload: any) {
    // const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(payload) });
    return this.httpClient.post(this.loginUrl, payload);
  }

  setHeader() {
    this.refreshToken = this.storageService.getEncodedToken().refreshToken;
    this.header = this.header.set('Authorization', 'Bearer ' + this.refreshToken);
    console.log(this.refreshToken);
    console.log(this.header);
  }

  getRefreshToken() {
    this.setHeader();
    console.log(this.header)
    return this.httpClient.post(this.refreshTokenUrl, {'headers': this.header});
  }
}
