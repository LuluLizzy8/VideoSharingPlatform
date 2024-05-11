//represent the response coming from the our backend
export interface VideoDto {
	id: string;
	userId: string; //necessary?
	title: string;
	description: string;
	videoUrl: string;
	thumbnailUrl: string;
	likes: number;
	viewCount: number;
	//commentList: Array<string>
}