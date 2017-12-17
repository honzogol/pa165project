import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';
import { HomeComponent } from './manager/home/home.component';
import { MonstersComponent } from './manager/monsters/monsters.component';
import { MonsterDetailComponent } from './manager/monster-detail/monster-detail.component';
import { WeaponsComponent} from './manager/weapons/weapons.component';

import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { ManagerComponent } from './manager/manager.component';
import {WeaponDetailComponent} from "./manager/weapon-detail/weapon-detail.component";
import {UsersComponent} from "./manager/users/users.component";
import {WeaponCreateComponent} from "./manager/weapon-create/weapon-create.component";
import {MonsterCreateComponent} from "./manager/monster-create/monster-create.component";
import {Error404Component} from "./error404/error404.component";
import {UserCreateComponent} from "./manager/user-create/user-create.component";
import {AreaCreateComponent} from "./manager/area-create/area-create.component";
import {AreaDetailComponent} from "./manager/area-detail/area-detail.component";
import {AreasComponent} from "./manager/areas/areas.component";
import {CookieService} from "ngx-cookie-service";
import {CookieLawModule} from "angular2-cookie-law";
import { AddMonstersComponent } from './add-monsters-dialog/add-monsters-dialog.component';
import {MatStepperModule} from "@angular/material";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CONFIG, CONFIG_TOKEN} from "./app-config";
import { ErrorDialogComponent } from './error-dialog/error-dialog.component';
import {ErrorInterceptor} from "./error-interceptor";


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MonstersComponent,
    WeaponsComponent,
    UsersComponent,
    AreasComponent,
    MonsterDetailComponent,
    WeaponDetailComponent,
    AreaDetailComponent,
    WeaponCreateComponent,
    MonsterCreateComponent,
    AreaCreateComponent,
    UserCreateComponent,
    LoginComponent,
    ManagerComponent,
    Error404Component,
    AddMonstersComponent,
    ErrorDialogComponent
  ],
  imports: [
    CookieLawModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    MatStepperModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  entryComponents: [
    AddMonstersComponent,
    ErrorDialogComponent,
  ],
  providers: [CookieService,
    {
      provide: CONFIG_TOKEN,
      useValue: CONFIG
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true,
    }
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
