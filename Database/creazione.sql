DROP TABLE IF EXISTS Added;
DROP TABLE IF EXISTS Featuring;
DROP TABLE IF EXISTS FollowArtist;
DROP TABLE IF EXISTS FollowUser;
DROP TABLE IF EXISTS Guests;
DROP TABLE IF EXISTS Likes;
DROP TABLE IF EXISTS PlaylistCollaborative;
DROP TABLE IF EXISTS PlaylistPublic;
DROP TABLE IF EXISTS Plays;
DROP TABLE IF EXISTS Track;
DROP TABLE IF EXISTS Genre;
DROP TABLE IF EXISTS Album;
DROP TABLE IF EXISTS Playlist;
DROP TABLE IF EXISTS Overseer;
DROP TABLE IF EXISTS Artist;
DROP TABLE IF EXISTS EndUser;
DROP TABLE IF EXISTS Profile;
DROP TABLE IF EXISTS Nation;

CREATE TABLE Nation (
	iso CHAR(3) PRIMARY KEY,
	name VARCHAR(32) NOT NULL
);	

CREATE TABLE Profile (
    id INT AUTO_INCREMENT PRIMARY KEY,
	email VARCHAR(256) NOT NULL UNIQUE,
    password VARCHAR(32) NOT NULL,
	role CHAR(1) NOT NULL CHECK (role IN ('U', 'A', 'O'))
);

CREATE TABLE Overseer (
	profile INT NOT NULL PRIMARY KEY,
	FOREIGN KEY (profile) REFERENCES Profile(id)
);

CREATE TABLE EndUser (
	profile INT NOT NULL PRIMARY KEY,
	alias VARCHAR(128) NOT NULL,
	birthdate DATE NOT NULL,
	nation CHAR(3) NOT NULL,
	isPublic BIT DEFAULT 0,
	FOREIGN KEY (profile) REFERENCES Profile(id),
	FOREIGN KEY (nation) REFERENCES Nation(iso)
);

CREATE TABLE Playlist (
	enduser INT NOT NULL,
	id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	title VARCHAR(128) NOT NULL,
	tracks INT DEFAULT 0,
	duration INT DEFAULT 0,
	isPublic BIT DEFAULT 0,
	isCollaborative BIT DEFAULT 0,
	lastAccessTime DATETIME DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (enduser) REFERENCES EndUser(profile)
);

CREATE TABLE PlaylistPublic (
	playlist INT NOT NULL PRIMARY KEY,
	likes INT DEFAULT 0,
	FOREIGN KEY (playlist) REFERENCES Playlist(id)
);

CREATE TABLE PlaylistCollaborative (
	playlist INT NOT NULL PRIMARY KEY,
	FOREIGN KEY (playlist) REFERENCES Playlist(id)
);

CREATE TABLE Artist (
	profile INT NOT NULL PRIMARY KEY,
	alias VARCHAR(128) NOT NULL,
	bio VARCHAR(1024) NOT NULL,
	FOREIGN KEY (profile) REFERENCES Profile(id)
);

CREATE TABLE Album (
	id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	artist INT NOT NULL,
	title VARCHAR(128) NOT NULL,
	tracks INT NOT NULL,
	duration INT NOT NULL,
	year INT NOT NULL,
	type VARCHAR(8) NOT NULL CHECK (type IN ('Album', 'Single', 'EP')) DEFAULT 'Album',
	FOREIGN KEY (artist) REFERENCES Artist(profile),
	UNIQUE (artist, title)
);

CREATE TABLE Genre (
	id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	label VARCHAR(32) NOT NULL
);

CREATE TABLE Track (
	album INT NOT NULL,
	id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	title VARCHAR(128) NOT NULL,
	track INT NOT NULL,
	duration INT NOT NULL,
	plays INT DEFAULT 0,
	genre INT NOT NULL,
	FOREIGN KEY (album) REFERENCES Album(id),
	FOREIGN KEY (genre) REFERENCES Genre(id),
	UNIQUE(album, title)
);

CREATE TABLE Featuring (
	track INT NOT NULL,
	artist INT NOT NULL,
	FOREIGN KEY (track) REFERENCES Track(id),
	FOREIGN KEY (artist) REFERENCES Artist(profile),
	PRIMARY KEY (track, artist)
);

CREATE TABLE FollowArtist (
	enduser INT NOT NULL,
	artist INT NOT NULL,
	FOREIGN KEY (enduser) REFERENCES EndUser(profile),
	FOREIGN KEY (artist) REFERENCES Artist(profile),
	PRIMARY KEY (enduser, artist)
);

CREATE TABLE FollowUser (
	follower INT NOT NULL,
	followed INT NOT NULL,
	FOREIGN KEY (follower) REFERENCES EndUser(profile),
	FOREIGN KEY (followed) REFERENCES EndUser(profile),
	PRIMARY KEY (follower, followed)
);

CREATE TABLE Plays (
	enduser INT NOT NULL,
	track INT NOT NULL,
	time DATETIME DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (enduser) REFERENCES EndUser(profile),
	FOREIGN KEY (track) REFERENCES Track(id),
	PRIMARY KEY (enduser, track, time)
);

CREATE TABLE Added (
	enduser INT NOT NULL,
	track INT NOT NULL,
	playlist INT NOT NULL,
	date DATETIME DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (enduser) REFERENCES EndUser(profile),
	FOREIGN KEY (track) REFERENCES Track(id),
	FOREIGN KEY (playlist) REFERENCES Playlist(id),
	PRIMARY KEY (track, playlist)
);

CREATE TABLE Likes (
	enduser INT NOT NULL,
	playlist INT NOT NULL,
	FOREIGN KEY (enduser) REFERENCES EndUser(profile),
	FOREIGN KEY (playlist) REFERENCES PlaylistPublic(playlist),
	PRIMARY KEY (enduser, playlist)
);

CREATE TABLE Guests (
	host INT NOT NULL,
	guest INT NOT NULL,
	playlist INT NOT NULL,
	FOREIGN KEY (host) REFERENCES EndUser(profile),
	FOREIGN KEY (guest) REFERENCES EndUser(profile),
	FOREIGN KEY (playlist) REFERENCES PlaylistCollaborative(playlist),
	PRIMARY KEY (host, guest, playlist)
);