import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {StorageService} from "./storage.service";
import {LoginService} from "./login.service";
import {Transit} from "../model/transit";
import {Observable} from "rxjs";
import {Passenger} from "../model/passenger";
import {Bus} from "../model/bus";

@Injectable({
  providedIn: 'root'
})
export class BusService {
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

  public getBuses(page: number) {
    this.setHeader();
    return this.http.get<Bus[]>(`${this.apiUrl}/buses?page=` + page + '&sort=DESC', {'headers': this.header})
  }

  public getTransit(id: bigint): Observable<Bus> {
    this.setHeader();
    return this.http.get<Bus>(`${this.apiUrl}/buses/${id}`, {'headers': this.header});
  }

  addBus(bus: Bus): Observable<Bus> {
    return this.http.post<Bus>(`${this.apiUrl}/buses`, bus, {'headers': this.header})
  }
}
