/*
 CSE 17
 Matthew Chin
 mjc714
 Program #5 DEADLINE: April 23, 2015
 Program Description: Digital Music Library with Advanced Sorting
 */

import java.util.*;

/**Song class, is a song that can be sorted by Title, artist, or year*/
public class Song{
  
  private String title;
  private String artist;
  private String album;
  private int time;
  private int year;
  
  /**SongTitleComparator inner class, implement compare from Comparator<T> to compare song titles*/
  public static class TitleComparator implements Comparator<Song>, java.io.Serializable{
    
    /**override compare method*/
    @Override
    public int compare(Song s1, Song s2){
      String title1 = s1.getTitle();
      String title2 = s2.getTitle();
      int dec = 0;
      if(title1.compareTo(title2) < 0){
        dec = -1;
      }
      else if(title1.equals(title2)){
        dec = 0;
      }
      else if(title1.compareTo(title2) > 0){
        dec = 1;
      }
      return dec;
    }//end compare
  }//end SongTitleComparator
  
  /**SongArtistComparator inner class, implement compare from Comparator<T> to compare song artists*/
  public static class ArtistComparator implements Comparator<Song>, java.io.Serializable{
    
    /**override compare method*/
    @Override
    public int compare(Song s1, Song s2){
      String artist1 = s1.getArtist();
      String artist2 = s2.getArtist();
      int dec = 0;
      if(artist1.compareTo(artist2) < 0){
        dec = -1;
      }
      else if(artist1.equals(artist2)){
        dec = 0;
      }
      else if(artist1.compareTo(artist2) > 0){
        dec = 1;
      }
      return dec;
    }//end compare
  }//end SongArtistComparator class
  
  /**SongYearComparator inner class implement compare from Comparator<T> to compare song years*/
  public static class YearComparator implements Comparator<Song>, java.io.Serializable{
    
    /**override compare method*/
    @Override
    public int compare(Song s1, Song s2){
      int year1 = s1.getYear();
      int year2 = s2.getYear();
      int dec = 0;
      if(year1 < year2){
        dec = -1;
      }
      else if(year1 == year2){
        dec = 0;
      }
      else if(year1 > year2){
        dec = 1;
      }
      return dec;
    }//end compare
  }//end SongYearComparator class
  
  /**SongTitleComparator, constant instance with compare() method that orders titles ascending*/
  public static final Comparator<Song> SongTitleComparator(){
    return new TitleComparator();
  }
  /**SongArtistComparator, constant instance with a compare() method that orders artits ascending*/
  public static final Comparator<Song> SongArtistComparator(){
    return new ArtistComparator();
  }
  /**SongYearComparator, constant instance with a compare() method that orders years ascending*/
  public static final Comparator<Song> SongYearComparator(){
    return new YearComparator();
  }
  /**Song() constructor*/
  public Song(String title, String artist, String album, int year, int time){
    this.title = title;
    this.artist = artist;
    this.album = album;
    this.year = year;
    this.time = time;  //seconds
  }//end Song constructor
  
  public Song(String title, String artist, String album, int year, String timeString){
    this.title = title;
    this.artist = artist;
    this.album = album;
    this.year = year;
  }//end Song() constructor
  
  /**getTitle(), returns title*/
  public String getTitle(){
    return title;
  }
  /**getArtist(), returns artist*/
  public String getArtist(){
    return artist;
  }
  /**getAlbum(), returns album*/
  public String getAlbum(){
    return album;
  }
  /**getYear(), returns year*/
  public int getYear(){
    return year;
  }
  /**getTime(), returns time*/
  public int getTime(){
    return time;
  }
  /**getTimeasFormattedString, return the time of the song as a string in mm:ss format*/
  public String getTimeasFormattedString(){
    String timeFormat = " ";
    int time = this.getTime();
    int minutes = time/60;
    int seconds = (time-(minutes *60));
    if(seconds < 10){
      timeFormat = (minutes + ":" + "0" + seconds);
    }//end if
    else{
      timeFormat = (minutes + ":" + seconds);
    }//end else
    return timeFormat;
  }//end getTimeasFormattedString()
  /**printSongRow(), prints the information about a song on a single line
    * line should print the fields in the following order:
    * song title(at least 25 chars)
    * artist (at least 25 chars)
    * album (at least 30 chars)
    * year, and time(mm:ss format), each field has a \t char between it*/
  public void printSongRow(){
    String stringYear = Integer.toString(this.year);
    System.out.printf("%-30s\t%-25s\t%-30s\t%-10s\t%s\t\n", this.title, this.artist, this.album, stringYear, this.getTimeasFormattedString()); 
  }
  /**toString(), return a string of the form title by artist (year)*/
  @Override
  public String toString(){
    System.out.println( "\"" + this.getTitle() + "\"" + " by " + this.getArtist() + " (" + this.getYear() + ")"); 
    return "";
  }
  /**timeStringToSecs(), given a string of the form mm:ss returns an integer
    * representing the time as a total number of seconds*/
  public static int timeStringToSecs(String timeString){
    int timeInSeconds = 0;
    int minutes = 0;
    int seconds = 0;
    if(timeString.length() == 5){
      minutes = Integer.parseInt(timeString.substring(0,2));
      seconds = Integer.parseInt(timeString.substring(3,5));
      timeInSeconds = (minutes*60) + seconds;
    }
    else if(timeString.length() == 4){
      minutes = Integer.parseInt(timeString.substring(0,1));
      seconds = Integer.parseInt(timeString.substring(2,4));
      timeInSeconds = (minutes*60) + seconds;
    }
    return timeInSeconds;
  }
}//end Song class