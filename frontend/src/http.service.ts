
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Note} from './Note';

@Injectable()
export class HttpService{

  constructor(private http: HttpClient){ }

  postData(note: Note){

    const body = {name: note.name, description: note.description};
    return this.http.post("http://localhost:8080/api/note", body);
  }
}
