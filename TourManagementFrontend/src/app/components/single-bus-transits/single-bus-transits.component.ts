import {Component, OnInit} from '@angular/core';
import {Transit} from "../../model/transit";
import {TransitService} from "../../service/transit.service";
import {ActivatedRoute, Router} from "@angular/router";
import {StorageService} from "../../service/storage.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-single-bus-transits',
  templateUrl: './single-bus-transits.component.html',
  styleUrls: ['./single-bus-transits.component.css']
})
export class SingleBusTransitsComponent implements OnInit {
  // @ts-ignore
  public transits: Transit[];
  page: number = 0;
  // @ts-ignore
  transitId: bigint
  isLoggedIn = false;
  // @ts-ignore
  email: string
  showAdminBoard = false;

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
      this.roles = this.storageService.getDecodedJwt().roles;
      // @ts-ignore
      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
    }
    // @ts-ignore
    this.getTransitsForBus(this.route.snapshot.paramMap.get('id'), this.page);
    console.log(this.showAdminBoard)
  }

  nextPage() {
    this.page++;
    // @ts-ignore
    this.getTransitsForBus(this.route.snapshot.paramMap.get('id'), this.page);
  }

  previousPage() {
    this.page--;
    // @ts-ignore
    this.getTransitsForBus(this.route.snapshot.paramMap.get('id'), this.page);
  }

  public getTransitsForBus(id: bigint) {
    // @ts-ignore
    this.transitService.getTransitsForBus(id, this.page).subscribe(
      (response: Transit[]) => {
        this.transits = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
