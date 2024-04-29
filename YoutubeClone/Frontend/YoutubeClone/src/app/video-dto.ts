//represent the response coming from the our backend
export interface VideoDto {
	id: string;
	userId: string; //necessary?
	title: string;
	description: string;
	videoUrl: string;
	thumbnailUrl: string;
	likes: string; //how to get integer?
	commentList: Array<string>
}