import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UploadVideoComponent } from './upload-video/upload-video.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, withFetch, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgxFileDropModule } from 'ngx-file-drop';
import { MatButtonModule } from "@angular/material/button";
import { HeaderComponent } from './header/header.component';
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatIconModule } from "@angular/material/icon";
import { SaveVideoDetailsComponent } from './save-video-details/save-video-details.component';
import { FlexLayoutModule } from "@angular/flex-layout";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatSelectModule } from "@angular/material/select";
import { MatOptionModule } from "@angular/material/core";
import { MatInputModule } from "@angular/material/input";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { VgCoreModule } from "@videogular/ngx-videogular/core";
import { VgControlsModule } from "@videogular/ngx-videogular/controls";
import { VgOverlayPlayModule } from "@videogular/ngx-videogular/overlay-play";
import { VgBufferingModule } from "@videogular/ngx-videogular/buffering";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { VideoPlayerComponent } from './video-player/video-player.component';
import { VideoDetailComponent } from './video-detail/video-detail.component';
import { AuthConfigModule } from './auth/auth-config.module';
import { AuthInterceptor, AuthModule } from 'angular-auth-oidc-client';
import { HomeComponent } from './home/home.component';
import { HistoryComponent } from './history/history.component';
import { SubscriptionsComponent } from './subscriptions/subscriptions.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatListModule } from "@angular/material/list";
import { FeaturedComponent } from './featured/featured.component';
import { VideoCardComponent } from './video-card/video-card.component';
import { MatCardModule } from "@angular/material/card";
import { CallbackComponent } from './callback/callback.component';
import { MatMenuModule } from "@angular/material/menu";
import { ProfileComponent } from './profile/profile.component'; 

@NgModule({
  declarations: [
    AppComponent,
    UploadVideoComponent,
    HeaderComponent,
    SaveVideoDetailsComponent,
    VideoPlayerComponent,
    VideoDetailComponent,
    HomeComponent,
    HistoryComponent,
    SubscriptionsComponent,
    SidebarComponent,
    FeaturedComponent,
    VideoCardComponent,
    CallbackComponent,
    ProfileComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgxFileDropModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    FlexLayoutModule,
    MatFormFieldModule, 
    MatSelectModule,
    MatOptionModule, 
    MatInputModule,
    ReactiveFormsModule, 
    VgCoreModule,
    VgControlsModule,
    VgOverlayPlayModule,
    VgBufferingModule,
    MatSnackBarModule,
    AuthConfigModule,
    MatSidenavModule,
    MatListModule,
    MatCardModule,
    MatMenuModule
  ],
  
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi:true}
  ],
  
  bootstrap: [AppComponent]
})
export class AppModule { }