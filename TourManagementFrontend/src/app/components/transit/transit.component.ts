import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TransitService} from "../../service/transit.service";
import {Transit} from "../../model/transit";
import {HttpErrorResponse} from "@angular/common/http";
import {StorageService} from "../../service/storage.service";

@Component({
  selector: 'app-transit',
  templateUrl: './transit.component.html',
  styleUrls: ['./transit.component.css']
})
export class TransitComponent implements OnInit {
  // @ts-ignore
  public transits: Transit[];
  page: number = 0;
  isLoggedIn = false;
  // @ts-ignore
  email: string

  constructor(private transitService: TransitService,
              private router: Router,
              private route: ActivatedRoute,
              private storageService: StorageService) {
  }

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();
    if (this.isLoggedIn) {
      this.email = this.storageService.getDecodedJwt().sub;
      // @ts-ignore
    }
    this.getTransits();
    console.log("A")
  }

  nextPage() {
    this.page++;
    this.getTransits();
  }

  previousPage() {
    this.page--;
    this.getTransits();
  }

  public getTransits() {
    this.transitService.getTransits(this.page).subscribe(
      (response: Transit[]) => {
        this.transits = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
