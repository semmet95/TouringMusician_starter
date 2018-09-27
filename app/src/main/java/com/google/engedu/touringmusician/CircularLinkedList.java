/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.touringmusician;


import android.graphics.Point;
import android.util.Log;

import java.util.Iterator;

public class CircularLinkedList implements Iterable<Point> {

    private class Node {
        Point point;
        Node prev, next;

        Node(Point p) {
            point=p;
            prev=next=null;
        }

        void addMeAfter(Node before) {
            this.next=before.next;
            this.prev=before;
            before.next.prev=this;
            before.next=this;
        }
    }

    Node head;

    public void insertBeginning(Point p) {
        Node to_add=new Node(p);
        if(!emptyList(p)) {
            //Log.e("insertbegining :", "head is not null");
            to_add.addMeAfter(head.prev);
            head=to_add;
        }
    }

    private float distanceBetween(Point from, Point to) {
        return (float) Math.sqrt(Math.pow(from.y-to.y, 2) + Math.pow(from.x-to.x, 2));
    }

    public float totalDistance() {
        float total = 0;
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        Point prev_p=null;
        for(Point p: this) {
            if(prev_p!=null)
                total+=distanceBetween(prev_p, p);
            prev_p=p;
        }

        return total;
    }

    public void insertNearest(Point p) {
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        Node to_add=new Node(p);
        if(!emptyList(p)) {
            Node closest=head;
            float dist=Float.MAX_VALUE;
            Node temp=head;
            while(true) {
                float curr_dist=distanceBetween(p, temp.point);
                if(curr_dist<dist) {
                    dist=curr_dist;
                    closest=temp;
                }
                temp=temp.next;
                if(temp==head)
                    break;
            }
            to_add.addMeAfter(closest);
        }
    }

    public void insertSmallest(Point p) {
        if(!emptyList(p)) {
            float total_dist = totalDistance();
            Node to_add = new Node(p), prev = head;
            float min_total = Float.MAX_VALUE, curr_total;
            curr_total=total_dist+distanceBetween(p, head.point);
            if(curr_total<min_total) {
                min_total=curr_total;
                prev=head.prev;
            }
            Node temp=head;
            while(true) {
                if(temp.next==head) {
                    curr_total=total_dist+distanceBetween(temp.point, p);
                    if(curr_total<min_total) {
                        prev=temp;
                    }
                    break;
                } else {
                    curr_total=total_dist-distanceBetween(temp.point, temp.next.point)+distanceBetween(temp.point, p)+distanceBetween(p, temp.next.point);
                    if(curr_total<min_total) {
                        min_total=curr_total;
                        prev=temp;
                    }
                }
                //Log.e("insertSmallest :", "moving temp forward");
                temp=temp.next;
            }
            to_add.addMeAfter(prev);
        }
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
    }

    public void reset() {
        head = null;
    }

    private boolean emptyList(Point p) {
        if(head!=null)
            return false;
        Node to_add=new Node(p);
        head=to_add;
        head.next=head;
        head.prev=head;
        return true;
    }

    private class CircularLinkedListIterator implements Iterator<Point> {

        Node current;

        public CircularLinkedListIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public Point next() {
            Point toReturn = current.point;
            current = current.next;
            if (current == head) {
                current = null;
            }
            return toReturn;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<Point> iterator() {
        return new CircularLinkedListIterator();
    }


}
