# About this Project

An android view that enable clip foreground and background into irregular shape

# Core Ability

- foreground drawable
- background drawable
- foreground border
- background border
- clip drawable into any shape
- clip by path or round corners

# Steps for Integration

#### 1. Dependency

```kotlin
api("io.github.hellogoogle2000:android-irregular-view:1.0.1")
```

#### 2. Apply in Xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/white"
    android:gravity="center"
    android:orientation="vertical">

    <com.android.library.irregularview.IrregularView
        android:id="@+id/irregularView1"
        android:layout_width="150dp"
        android:layout_height="120dp"
        android:background="#2200FF00"
        android:padding="5dp"
        app:foreground="@drawable/ic"
        app:foregroundBorderColor="#FF00FF"
        app:foregroundBorderWidth="5dp"
        app:foregroundOutline="Path"
        app:foregroundPathData="M 0 50 L 0 0 L 100 0 L 100 50 A 50 50 0 0 1 0 50 Z" />

    <com.android.library.irregularview.IrregularView
        android:id="@+id/irregularView2"
        android:layout_width="150dp"
        android:layout_height="120dp"
        android:layout_marginTop="10dp"
        android:background="#2200FF00"
        android:padding="5dp"
        app:foreground="@drawable/ic"
        app:foregroundBorderColor="#FF00FF"
        app:foregroundBorderWidth="5dp"
        app:foregroundBottomRightRadius="20%"
        app:foregroundOutline="RoundRect"
        app:foregroundTopLeftRadius="20%" />

    <com.android.library.irregularview.IrregularView
        android:id="@+id/irregularView3"
        android:layout_width="150dp"
        android:layout_height="150dp"
        android:layout_marginTop="10dp"
        app:background="@color/blue_percent_50"
        app:backgroundOutline="Path"
        app:backgroundPathData="M 50 0 A 50 50 0 0 0 50 100 A 50 50 0 0 0 50 0"
        app:foreground="@color/red_percent_50"
        app:foregroundCornerRadius="20%"
        app:foregroundOutline="RoundRect" />
</LinearLayout>
```

#### 3. Cautions

- path will be scaled to fill content size
- round corners is defined in fraction
- master basic knowledge of path, if you want to accomplish any shape 

#### 4. Supported Attributes

- foreground
- foregroundOutline
- foregroundPathData
- foregroundCornerRadius
- foregroundTopLeftRadius
- foregroundTopRightRadius
- foregroundBottomLeftRadius
- foregroundBottomRightRadius
- foregroundBorderColor
- foregroundBorderWidth
- background
- backgroundOutline
- backgroundPathData
- backgroundCornerRadius
- backgroundTopLeftRadius
- backgroundTopRightRadius
- backgroundBottomLeftRadius
- backgroundBottomRightRadius
- backgroundBorderColor
- backgroundBorderWidth

# Preview

<img src="https://github.com/user-attachments/assets/0a619512-7847-4d12-9edd-4e4440f097a0" height="750">