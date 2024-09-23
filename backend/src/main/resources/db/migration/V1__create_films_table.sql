create table films (
    id varchar(255) primary key,
    title varchar(255) not null,
    lengthInMinutes  decimal not null,
    moodType varchar(255) not null
);