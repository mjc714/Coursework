/*
 CSE 17
 Matthew Chin
 mjc714
 Program #5 DEADLINE: April 23, 2015
 Program Description: Digital Music Library with Advanced Sorting
 */

import java.util.*;
import java.io.*;

/**Playlist class, a collection of songs in a specified order, has a name and linked list of songs*/
public class Playlist{
  
  private String name;
  private ArrayList<Song> songs;
  
  /**Playlist constructor, intialize a new playlist with no songs*/
  public Playlist(String name){
    this.name = name;
    songs = new ArrayList<Song>();
  }//end Playlist construc
  
  /**getName(), return name of the playlist*/
  public String getName(){
    return name;
  }//end getName()
  
  /**getSong(), given an integer position, returns the song at that position,
    * let the first song be at position 1, 
    * if index is invalid, then null is returned*/
  public Song getSong(int index){
    if(index == 0 || index < 0){
      return null;
    }
    else{
      --index;
      return songs.get(index);
    }
  }//end getSong()
  
  /**getNumSongs(), return number of songs in the list*/
  public int getNumSongs(){
    int count = 0;
    for(int i = 0; i < songs.size(); i++){
      count++;
    }
    return count;
  }//end getNumSongs()
  
  /**addSong(), add a song to the end of songs ArrayList<Song>*/
  public void addSong(Song song){
    songs.add(song); 
  }//end addSong()
  
  /**printList(), outputs the name of the playlist and a sequence of lines with detailed information about each song
    * each song line should begin with the song's sequence number in the playlist(starting at 1)
    * and a tab character, the rest of the line is of the form used by printSongRow*/
  public void printList(){
    System.out.println(this.getName());
    for(int i = 0; i < songs.size(); i++){
      System.out.print((i+1) + "\t");
      songs.get(i).printSongRow();
    }//end for
    System.out.println();
  }//end printList()
  
  /**sortByYear(), invokes a merge sort to resort songs by year in ascending order*/
  public void sortByYear(){
    ComparatorSorts.mergeSort(songs, Song.SongYearComparator());
  }//end sortByYear()
  
  /**sortByArtist(), invokes a quick sort to resort songs by artist in ascending order*/
  public void sortByArtist(){
    ComparatorSorts.quickSort(songs, Song.SongArtistComparator());
  }//end sortByArtist()
  
  /**sortByTitle(), invokes a quick sort to resort songs by title in ascending order*/
  public void sortByTitle(){
    ComparatorSorts.quickSort(songs, Song.SongTitleComparator());
  }//end sortByTitle()
  
  /**readPlayListFromFile(), takes a string filename paramter and returns a Playlist object filled with the Songs 
    * described in the file*/
  public static Playlist readPlayListFromFile(String filename) throws FileNotFoundException{
    File file = new File(filename);
    Scanner fileScanner = new Scanner(file);
    int songCount = 0;
    fileScanner.nextLine();
    while(fileScanner.hasNext()){
      fileScanner.nextLine();
      songCount++;
    }
    
    Scanner fileScanner2 = new Scanner(file);
    fileScanner2.useDelimiter("\\t|[\\n\\r\\f]+");
    //get the playlistTitle, first line
    String playlistTitle = fileScanner2.nextLine();
    //create a new playlist
    Playlist playList = new Playlist(playlistTitle);
    String stringYear = " ";
    for(int i = 0; i < songCount; i++){
      String title = fileScanner2.next();
      String artist = fileScanner2.next();
      String album = fileScanner2.next();
      stringYear = fileScanner2.next();
      String timeString = fileScanner2.next();
      int year = Integer.parseInt(stringYear);
      Song song = new Song(title, artist, album, year, Song.timeStringToSecs(timeString));
      playList.addSong(song);
    }//end for
    return playList;
  }//end readPlayListFromFile()
  
  /**Main method*/
  public static void main(String[] args){
    try{
      if(args.length >= 1){
        switch(args.length){
          case 1:{ 
            File file1 = new File(args[0]);
            if(file1.exists()){
              Playlist play1 = Playlist.readPlayListFromFile(args[0]);
              play1.getName();
              play1.printList();
              
              //quickSort
              System.out.println("Sorting " + play1.getName() + " by artist: ");
              play1.sortByArtist();
              play1.printList();
              
              //mergeSort
              System.out.println("Sorting " + play1.getName() + " by year: ");
              play1.sortByYear();
              play1.printList();
              
              //first and last songs
              System.out.println("First and last song in " + play1.getName());
              play1.getSong(1).toString();
              play1.getSong(play1.getNumSongs()).toString();
              
              break;
            }
            else{
              throw new FileNotFoundException();
            }
          }
          case 2:{
            File file1 = new File(args[0]);
            File file2 = new File(args[1]);
            if(file1.exists() && file2.exists()){
              Playlist play1 = Playlist.readPlayListFromFile(args[0]);
              Playlist play2 = Playlist.readPlayListFromFile(args[1]);
              play1.getName();
              play1.printList();
              
              play2.getName();
              play2.printList();
              
              //quickSort
              System.out.println("Sorting " + play1.getName() + " by artist: ");
              play1.sortByArtist();
              play1.printList();
              
              //mergeSort
              System.out.println("Sorting " + play2.getName() + " by year: ");
              play2.sortByYear();
              play2.printList();
              
              //first and last songs
              System.out.println("First and last song in " + play1.getName());
              play1.getSong(1).toString();
              play1.getSong(play1.getNumSongs()).toString();
              break;
            }
            else{
              throw new FileNotFoundException();
            }
          }
          case 3:{
            File file1 = new File(args[0]);
            File file2 = new File(args[1]);
            File file3 = new File(args[2]);
            if(file1.exists() && file2.exists() && file3.exists()){
              Playlist play1 = Playlist.readPlayListFromFile(args[0]);
              Playlist play2 = Playlist.readPlayListFromFile(args[1]);
              Playlist play3 = Playlist.readPlayListFromFile(args[2]);
              
              play1.getName();
              play1.printList();
              
              play2.getName();
              play2.printList();
              
              play3.getName();
              play3.printList();
              
              //quickSort
              System.out.println("Sorting " + play1.getName() + " by artist: ");
              play1.sortByArtist();
              play1.printList();
              
              //mergeSort
              System.out.println("Sorting " + play3.getName() + " by year: ");
              play3.sortByYear();
              play3.printList();
              
              //first and last songs
              System.out.println("First and last song in " + play1.getName());
              play1.getSong(1).toString();
              play1.getSong(play1.getNumSongs()).toString();
              break;
            }
            else{
              throw new FileNotFoundException();
            }
          }
          case 4:{
            File file1 = new File(args[0]);
            File file2 = new File(args[1]);
            File file3 = new File(args[2]);
            File file4 = new File(args[3]);
            if(file1.exists() && file2.exists() && file3.exists() && file4.exists()){
              Playlist play1 = Playlist.readPlayListFromFile(args[0]);
              Playlist play2 = Playlist.readPlayListFromFile(args[1]);
              Playlist play3 = Playlist.readPlayListFromFile(args[2]);
              Playlist play4 = Playlist.readPlayListFromFile(args[3]);
              
              play1.getName();
              play1.printList();
              
              play2.getName();
              play2.printList();
              
              play3.getName();
              play3.printList();
              
              play4.getName();
              play4.printList();
              
              //quickSort
              System.out.println("Sorting " + play1.getName() + " by artist: ");
              play1.sortByArtist();
              play1.printList();
              
              //mergeSort
              System.out.println("Sorting " + play4.getName() + " by year: ");
              play4.sortByYear();
              play4.printList();
              
              //first and last songs
              System.out.println("First and last song in " + play1.getName());
              play1.getSong(1).toString();
              play1.getSong(play1.getNumSongs()).toString();
              break;
            }
            else{
              throw new FileNotFoundException();
            }
          }
          case 5:{
            File file1 = new File(args[0]);
            File file2 = new File(args[1]);
            File file3 = new File(args[2]);
            File file4 = new File(args[3]);
            File file5 = new File(args[4]);
            if(file1.exists() && file2.exists() && file3.exists() && file4.exists()
                 && file5.exists()){
              Playlist play1 = Playlist.readPlayListFromFile(args[0]);
              Playlist play2 = Playlist.readPlayListFromFile(args[1]);
              Playlist play3 = Playlist.readPlayListFromFile(args[2]);
              Playlist play4 = Playlist.readPlayListFromFile(args[3]);
              Playlist play5 = Playlist.readPlayListFromFile(args[4]);
              
              play1.getName();
              play1.printList();
              
              play2.getName();
              play2.printList();
              
              play3.getName();
              play3.printList();
              
              play4.getName();
              play4.printList();
              
              play5.getName();
              play5.printList();
              
              //quickSort
              System.out.println("Sorting " + play1.getName() + " by artist: ");
              play1.sortByArtist();
              play1.printList();
              
              //mergeSort
              System.out.println("Sorting " + play5.getName() + " by year: ");
              play5.sortByYear();
              play5.printList();
              
              //first and last songs
              System.out.println("First and last song in " + play1.getName());
              play1.getSong(1).toString();
              play1.getSong(play1.getNumSongs()).toString();
              
              break;
            }
            else{
              throw new FileNotFoundException();
            }
          }
          case 6:{
            File file1 = new File(args[0]);
            File file2 = new File(args[1]);
            File file3 = new File(args[2]);
            File file4 = new File(args[3]);
            File file5 = new File(args[4]);
            File file6 = new File(args[5]);
            if(file1.exists() && file2.exists() && file3.exists() && file4.exists()
                 && file5.exists() && file6.exists()){
              Playlist play1 = Playlist.readPlayListFromFile(args[0]);
              Playlist play2 = Playlist.readPlayListFromFile(args[1]);
              Playlist play3 = Playlist.readPlayListFromFile(args[2]);
              Playlist play4 = Playlist.readPlayListFromFile(args[3]);
              Playlist play5 = Playlist.readPlayListFromFile(args[4]);
              Playlist play6 = Playlist.readPlayListFromFile(args[5]);
              
              play1.getName();
              play1.printList();
              
              play2.getName();
              play2.printList();
              
              play3.getName();
              play3.printList();
              
              play4.getName();
              play4.printList();
              
              play5.getName();
              play5.printList();
              
              play6.getName();
              play6.printList();
              
              //quickSort
              System.out.println("Sorting " + play1.getName() + " by artist: ");
              play1.sortByArtist();
              play1.printList();
              
              //mergeSort
              System.out.println("Sorting " + play6.getName() + " by year: ");
              play6.sortByYear();
              play6.printList();
              
              //first and last songs
              System.out.println("First and last song in " + play1.getName());
              play1.getSong(1).toString();
              play1.getSong(play1.getNumSongs()).toString();
              break;
            }
            else{
              throw new FileNotFoundException();
            }
          }
        }//end switch
      }//end if
      else{
        System.out.println("Usage: filename.txt");
        System.exit(1);
      }//end else
    }//try
    catch(FileNotFoundException ex){
      System.out.println("File Not Found!!");
      System.exit(1);
    }//catch
  }//end main
}//end Playlist class