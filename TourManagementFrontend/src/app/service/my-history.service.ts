import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {StorageService} from "./storage.service";
import {Transit} from "../model/transit";
import {MyHistory} from "../model/my-history";

@Injectable({
  providedIn: 'root'
})
export class MyHistoryService {
  private apiUrl = 'http://localhost:8080'
  private header = new HttpHeaders();
  private token: any;

  constructor(private http: HttpClient, private storageService: StorageService) { }

  private setHeader() {
    this.token = this.storageService.getEncodedToken().accessToken;
    this.header = this.header.set('Authorization', 'Bearer ' + this.token);
    console.log(this.header);
  }

  public getMyHistory(page: number) {
    this.setHeader();
    return this.http.get<MyHistory[]>(`${this.apiUrl}/users/9/history?page=` + page, {'headers': this.header})
  }
}
