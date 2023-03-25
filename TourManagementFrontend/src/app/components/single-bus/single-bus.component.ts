import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {StorageService} from "../../service/storage.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Bus} from "../../model/bus";
import {BusService} from "../../service/bus.service";

@Component({
  selector: 'app-single-bus',
  templateUrl: './single-bus.component.html',
  styleUrls: ['./single-bus.component.css']
})
export class SingleBusComponent implements OnInit {
  // @ts-ignore
  bus: Bus
  // @ts-ignore
  numberOfSeats: number
  isLoggedIn = false;
  // @ts-ignore
  email: string
  showAdminBoard = false;

  constructor(private busService: BusService,
              private router: Router,
              private route: ActivatedRoute,
              private storageService: StorageService) {}

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
    this.getTransit(this.route.snapshot.paramMap.get('id'))
  }

  public getTransit(id: bigint): void {
    this.busService.getTransit(id).subscribe(
      (response: Bus) => {
        console.log(response);
        this.bus = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
