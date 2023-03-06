import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Transit} from "../model/transit";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080'
  private header = new HttpHeaders();
  private token: any;

  constructor(private httpClient: HttpClient) { }

  public getId(username: string): Observable<any> {
    return this.httpClient.get<any>(`${this.apiUrl}/ids/${username}`);
  }
}
