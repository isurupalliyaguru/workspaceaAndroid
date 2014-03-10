package com.ipaintpad.drawings;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;


public class CommandManager {
    private List<DrawingPath> currentStack;
    private List<DrawingPath> redoStack;

    public  CommandManager(){
    	//getting all drawing paths to a list
        currentStack = Collections.synchronizedList(new ArrayList<DrawingPath>());
        redoStack = Collections.synchronizedList(new ArrayList<DrawingPath>());
    }

    public void addCommand(DrawingPath command){
        redoStack.clear();
        currentStack.add(command);
    }

    public void undo (){
    	//first get the length of the current stack
        final int length = currentStackLength();
        
        if ( length > 0) { // if the lengh is larger than zero (something is drawn)
            final DrawingPath undoCommand = currentStack.get(  length - 1  );
            currentStack.remove( length - 1 );// remove one from the list which removes the last added item.
            undoCommand.undo();
            redoStack.add( undoCommand );
        }
    }

    public int currentStackLength(){
        final int length = currentStack.toArray().length;
        return length;
    }


    public void executeAll( Canvas canvas){
        if( currentStack != null ){
            synchronized( currentStack ) {
                final Iterator i = currentStack.iterator();
                Log.d("aaa",currentStack.toArray().length + "");
                while ( i.hasNext() ){
                    final DrawingPath drawingPath = (DrawingPath) i.next();
                    drawingPath.draw( canvas ); // draw results
                }
            }
        }
    }



    public boolean hasMoreRedo(){ // checks if more items exists in the current list of items
        return  redoStack.toArray().length > 0;
    }

    public boolean hasMoreUndo(){ // checks if more items exists in the current list of items
        return  currentStack.toArray().length > 0;
    }

    public void redo(){
        final int length = redoStack.toArray().length;
        if ( length > 0) {
            final DrawingPath redoCommand = redoStack.get(  length - 1  );
            redoStack.remove( length - 1 );
            currentStack.add( redoCommand );
        }
    }
}
