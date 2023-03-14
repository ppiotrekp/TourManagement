import {Component, OnInit} from '@angular/core';
import {Transit} from "../../model/transit";
import {MyHistory} from "../../model/my-history";
import {TransitService} from "../../service/transit.service";
import {ActivatedRoute, Router} from "@angular/router";
import {StorageService} from "../../service/storage.service";
import {MyHistoryService} from "../../service/my-history.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-my-history',
  templateUrl: './my-history.component.html',
  styleUrls: ['./my-history.component.css']
})
export class MyHistoryComponent implements OnInit{
  // @ts-ignore
  public myHistories: MyHistory[];
  page: number = 0;
  isLoggedIn = false;
  // @ts-ignore
  email: string
  showAdminBoard = false;

  constructor(private historyService: MyHistoryService,
              private router: Router,
              private route: ActivatedRoute,
              private storageService: StorageService) {
  }

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();
    if (this.isLoggedIn) {
      this.email = this.storageService.getDecodedJwt().sub;
      // @ts-ignore
      this.roles = this.storageService.getDecodedJwt().roles;
      // @ts-ignore
      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
    }
    this.getHistory();
  }

  public getHistory() {
    this.historyService.getMyHistory(this.page).subscribe(
      (response: MyHistory[]) => {
        this.myHistories = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  nextPage() {
    this.page++;
    this.getHistory();
  }

  previousPage() {
    this.page--;
    this.getHistory();
  }
}
