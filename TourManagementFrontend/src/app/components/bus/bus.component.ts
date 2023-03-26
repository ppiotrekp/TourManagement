import {Component, OnInit} from '@angular/core';
import {Bus} from "../../model/bus";
import {ActivatedRoute, Router} from "@angular/router";
import {StorageService} from "../../service/storage.service";
import {HttpErrorResponse} from "@angular/common/http";
import {BusService} from "../../service/bus.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-bus',
  templateUrl: './bus.component.html',
  styleUrls: ['./bus.component.css']
})
export class BusComponent implements OnInit{
  // @ts-ignore
  public buses: Bus[];
  page: number = 0;
  isLoggedIn = false;
  // @ts-ignore
  email: string
  showAdminBoard = false;

  constructor(private busService: BusService,
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
    this.getBuses();
    console.log(this.showAdminBoard)
  }

  nextPage() {
    this.page++;
    this.getBuses();
  }

  previousPage() {
    this.page--;
    this.getBuses();
  }

  public getBuses() {
    this.busService.getBuses(this.page).subscribe(
      (response: Bus[]) => {
        this.buses = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  openModal() {
    const modalBody = document.querySelector("#my-modal")
    // @ts-ignore
    modalBody.style.display = 'block';
  }

  closeModal() {
    const modalBody = document.querySelector("#my-modal")
    // @ts-ignore
    modalBody.style.display = 'none';
  }

  public addBus(addForm: NgForm): void {
    // @ts-ignore
    let none = document.querySelector("#my-modal").style.display = 'none';
    this.busService.addBus(addForm.value).subscribe(
      (response: Bus) => {
        console.log(response);
        this.getBuses();
        addForm.reset();
        none;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
        none;
      }
    );
  }
}
