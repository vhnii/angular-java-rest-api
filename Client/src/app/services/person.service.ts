import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

import {Person} from '../Types/Person';

@Injectable({
  providedIn: 'root'
})

export class PersonService {

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


  constructor(private http:HttpClient) {}

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

  
  getPersons(): Observable<Person[]> {
    return this.http.get<Person[]>(`http://localhost:8080/api/persons`)
      .pipe(
        catchError(this.handleError<Person[]>('getPersons', []))
      );
  }

  addPersons(person: Person): Observable<Person> {

    return this.http.post<Person>(`${environment.apiUrl}/person`, person, this.httpOptions)
    .pipe(
      catchError((error: any) => {
          throw new Error('Failed to add Person.');
      })
    );
  }
}
