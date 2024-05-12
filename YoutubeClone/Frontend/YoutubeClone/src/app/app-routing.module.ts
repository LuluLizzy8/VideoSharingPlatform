import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UploadVideoComponent } from './upload-video/upload-video.component';
import { SaveVideoDetailsComponent} from './save-video-details/save-video-details.component';
import { VideoDetailComponent } from './video-detail/video-detail.component';
import { HomeComponent} from "./home/home.component";
import { SubscriptionsComponent } from "./subscriptions/subscriptions.component";
import { HistoryComponent } from "./history/history.component";
import { FeaturedComponent } from "./featured/featured.component";
import { ProfileComponent } from './profile/profile.component';

const routes: Routes = [
	{ path: '', component: HomeComponent,
	children:[
		{ path: 'subscriptions', component: SubscriptionsComponent, },
		{ path: 'history', component: HistoryComponent, },
		{ path: 'featured', component: FeaturedComponent, },
		{ path: 'video-detail/:videoId', component: VideoDetailComponent, },
		{ path: 'profile', component: ProfileComponent, },
		{ path: 'upload-video', component: UploadVideoComponent, },
		{ path: 'save-video-details/:videoId', component: SaveVideoDetailsComponent, },
	]},
	
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
