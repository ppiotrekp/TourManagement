import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Transit} from "../model/transit";
import {StorageService} from "./storage.service";
import {LoginService} from "./login.service";
import {User} from "../model/user";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080'
  private header = new HttpHeaders();
  private token: any;



  constructor(private http: HttpClient,
              private storageService: StorageService,
              private loginService: LoginService,) {}

  private tokenExpired(token: string) {
    const expiry = (JSON.parse(atob(token.split('.')[1]))).exp;
    return (Math.floor((new Date).getTime() / 1000)) >= expiry;
  }

  private setHeader() {
    this.token = this.storageService.getEncodedToken().accessToken;
    this.header = this.header.set('Authorization', 'Bearer ' + this.token);
    console.log(this.header);

    if (this.tokenExpired(this.token)) {
      this.loginService.getRefreshToken().subscribe(data => {
        console.log(data);
        this.storageService.saveJwt(data);
      })
    }
  }

  public getId(username: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/ids/${username}`);
  }

  public getUsers(page: number) {
    this.setHeader();
    return this.http.get<User[]>(`${this.apiUrl}/users?page=` + page, {'headers': this.header})
  }

  public getUser(id: bigint): Observable<User> {
    this.setHeader();
    return this.http.get<User>(`${this.apiUrl}/users/${id}`, {'headers': this.header});
  }
}
