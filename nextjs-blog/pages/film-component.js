import React, { useEffect, useState } from 'react';
import axios from 'axios';

const FilmsComponent = () => {
    const [films, setFilms] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:3001/films')  // Backend URL
            .then(response => {
                setFilms(response.data);
            })
            .catch(error => {
                console.error("There was an error fetching the films!", error);
            });
    }, []);

    return (
        <div>
            <h1>Films</h1>
            <ul>
                {films.map(film => (
                    <li key={film.id}>{film.title}</li>
                ))}
            </ul>
        </div>
    );
}

export default FilmsComponent;
