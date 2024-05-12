import { Component } from '@angular/core';
import { NgxFileDropEntry, FileSystemFileEntry, FileSystemDirectoryEntry } from 'ngx-file-drop';
import { VideoService } from '../video.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from '../user.service';


@Component({
  selector: 'app-upload-video',
  templateUrl: './upload-video.component.html',
  styleUrls: ['./upload-video.component.css']
})
export class UploadVideoComponent {

  public files: NgxFileDropEntry[] = [];
  fileUploaded: boolean = false;
  fileEntry: FileSystemFileEntry | undefined;
  isAuthenticated: boolean = false;

  
  constructor(private videoService: VideoService, private router: Router, private matSnackBar: MatSnackBar, private userService: UserService){
	if(this.userService.getUserId() != ""){
			this.isAuthenticated = true;
		}
  }
  
  public dropped(files: NgxFileDropEntry[]) {
    this.files = files;
    for (const droppedFile of files) {

      // Is it a file?
      if (droppedFile.fileEntry.isFile) {
        this.fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        this.fileEntry.file((file: File) => {

          // Here you can access the real file
          console.log(droppedFile.relativePath, file);
			
			
		  this.fileUploaded = true;
		

        });
      } else {
        // It was a directory (empty directories are added, otherwise only files)
        const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
        console.log(droppedFile.relativePath, fileEntry);
      }
    }
  }

  public fileOver(event: any){
    console.log(event);
  }

  public fileLeave(event: any){
    console.log(event);
  }
  
  uploadVideo(){
	if(this.isAuthenticated){
		if(this.fileEntry !== undefined){
			console.log(this.fileEntry);
			
			this.fileEntry.file(file => {this.videoService.uploadVideo(file).subscribe(data =>{
				//http://localhost:4200/save-video-details/{{videoId}}
				console.log(data.videoId)
				this.router.navigateByUrl("/save-video-details/" + data.videoId);
	
				})
			})
			
		}
	} else{
		this.matSnackBar.open("Login to Upload Videos", "OK");
	}
  }
}