/*
 * Copyright (C) 2014 The Android Open Source Project
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
 */

package com.android.inputmethod.event;

import com.android.inputmethod.latin.settings.SettingsValues;

/**
 * An object encapsulating a single transaction for input.
 */
public class InputTransaction {
    // UPDATE_LATER is stronger than UPDATE_NOW. The reason for this is, if we have to update later,
    // it's because something will change that we can't evaluate now, which means that even if we
    // re-evaluate now we'll have to do it again later. The only case where that wouldn't apply
    // would be if we needed to update now to find out the new state right away, but then we
    // can't do it with this deferred mechanism anyway.
    public static final int SHIFT_NO_UPDATE = 0;
    public static final int SHIFT_UPDATE_NOW = 1;
    public static final int SHIFT_UPDATE_LATER = 2;

    // Initial conditions
    public final SettingsValues mSettingsValues;
    // If the key inserts a code point, mKeyCode is always equal to the code points. Otherwise,
    // it's always a code that may not be a code point, typically a negative number.
    public final int mKeyCode;
    public final int mX; // Pressed x-coordinate, or one of Constants.*_COORDINATE
    public final int mY; // Pressed y-coordinate, or one of Constants.*_COORDINATE
    public final long mTimestamp;
    public final int mSpaceState;
    public final int mShiftState;

    // Outputs
    private int mRequiredShiftUpdate = SHIFT_NO_UPDATE;

    public InputTransaction(final SettingsValues settingsValues, final int keyCode,
            final int x, final int y, final long timestamp, final int spaceState,
            final int shiftState) {
        mSettingsValues = settingsValues;
        mKeyCode = keyCode;
        mX = x;
        mY = y;
        mTimestamp = timestamp;
        mSpaceState = spaceState;
        mShiftState = shiftState;
    }

    public void requireShiftUpdate(final int updateType) {
        mRequiredShiftUpdate = Math.max(mRequiredShiftUpdate, updateType);
    }
    public int getRequiredShiftUpdate() {
        return mRequiredShiftUpdate;
    }
}