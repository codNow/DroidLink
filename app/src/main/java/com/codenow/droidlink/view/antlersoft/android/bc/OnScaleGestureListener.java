/* Copyright (C) 2010 The Android Open Source Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * MODIFIED FOR ANTLERSOFT
 * 
 * Changes for antlersoft/ vnc viewer for android
 * 
 * Copyright (C) 2010 Michael A. MacDonald
 *
 * This is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this software; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,
 * USA.
 */
package com.codenow.droidlink.view.antlersoft.android.bc;



public interface OnScaleGestureListener {

    public boolean onScale(IBCScaleGestureDetector detector);

    /**
     * Responds to the beginning of a scaling gesture. Reported by
     * new pointers going down.
     * 
     * @param detector The detector reporting the event - use this to
     *          retrieve extended info about event state.
     * @return Whether or not the detector should continue recognizing
     *          this gesture. For example, if a gesture is beginning
     *          with a focal point outside of a region where it makes
     *          sense, onScaleBegin() may return false to ignore the
     *          rest of the gesture.
     */
    public boolean onScaleBegin(IBCScaleGestureDetector detector);

    /**
     * Responds to the end of a scale gesture. Reported by existing
     * pointers going up.
     * 
     * Once a scale has ended, {@link ScaleGestureDetector#getFocusX()}
     * and {@link ScaleGestureDetector#getFocusY()} will return the location
     * of the pointer remaining on the screen.
     * 
     * @param detector The detector reporting the event - use this to
     *          retrieve extended info about event state.
     */
    public void onScaleEnd(IBCScaleGestureDetector detector);
}