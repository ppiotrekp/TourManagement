import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BusComponent } from './components/bus/bus.component';
import { HomeComponent } from './components/home/home.component';
import {RouterModule, Routes} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import { LoginComponent } from './components/login/login.component';
import { TransitComponent } from './components/transit/transit.component';

const appRoute: Routes = [
  {path : '',redirectTo : 'home',pathMatch : 'full'},
  {path: 'home', component:HomeComponent},
  {path: 'login', component: LoginComponent },
  {path: 'transits', component: TransitComponent },
]

@NgModule({
  declarations: [
    AppComponent,
    BusComponent,
    HomeComponent,
    LoginComponent,
    TransitComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(appRoute),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
