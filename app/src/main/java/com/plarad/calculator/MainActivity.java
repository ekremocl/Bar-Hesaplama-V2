<?xml version="1.0" encoding="utf-8"?>

<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scaleType="centerCrop"
        android:src="@drawable/wind_turbine" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:gravity="center"
        android:background="#88000000"
        android:padding="24dp">

        <LinearLayout
            android:layout_width="320dp"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:gravity="center_horizontal"
            android:background="@drawable/header_card"
            android:padding="24dp"
            android:layout_marginBottom="20dp">

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Hydraulic Pressure Calculator"
                android:textColor="#FFFFFF"
                android:textSize="24sp"
                android:textStyle="bold"
                android:gravity="center"
                android:textAlignment="center"
                android:lineSpacingExtra="4dp" />

        </LinearLayout>

        <LinearLayout
            android:layout_width="320dp"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:layout_marginBottom="20dp">

            <Button
                android:id="@+id/openD4K"
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:text="D4K PDF"
                android:textStyle="bold"
                android:textColor="#FFFFFF"
                android:backgroundTint="#1565C0" />

            <Button
                android:id="@+id/openD3K"
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:layout_marginTop="10dp"
                android:text="D3K PDF"
                android:textStyle="bold"
                android:textColor="#FFFFFF"
                android:backgroundTint="#1565C0" />

            <Button
                android:id="@+id/openITH"
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:layout_marginTop="10dp"
                android:text="ITH TORK CHART"
                android:textStyle="bold"
                android:textColor="#FFFFFF"
                android:backgroundTint="#1565C0" />

        </LinearLayout>

        <EditText
            android:id="@+id/maxBar"
            android:layout_width="320dp"
            android:layout_height="wrap_content"
            android:hint="SİLİNDİR-KASET MAX BAR"
            android:background="#FFFFFF"
            android:gravity="center"
            android:inputType="numberDecimal" />

        <EditText
            android:id="@+id/instruction"
            android:layout_width="320dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="15dp"
            android:hint="INSTRUCTION KN-NM"
            android:background="#FFFFFF"
            android:gravity="center"
            android:inputType="numberDecimal" />

        <EditText
            android:id="@+id/maxKn"
            android:layout_width="320dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="15dp"
            android:hint="SİLİNDİR-KASET MAX KN-NM"
            android:background="#FFFFFF"
            android:gravity="center"
            android:inputType="numberDecimal" />

        <Button
            android:id="@+id/calculate"
            android:layout_width="320dp"
            android:layout_height="55dp"
            android:layout_marginTop="20dp"
            android:text="HESAPLA"
            android:textStyle="bold"
            android:textColor="#FFFFFF"
            android:backgroundTint="#1565C0" />

        <LinearLayout
            android:layout_width="320dp"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:orientation="vertical"
            android:padding="15dp"
            android:layout_marginTop="25dp"
            android:background="@drawable/header_card">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="RESULT"
                android:textColor="#FFFFFF"
                android:textStyle="bold" />

            <TextView
                android:id="@+id/result"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="0.00 BAR"
                android:textColor="#00FF88"
                android:textSize="42sp"
                android:textStyle="bold" />

        </LinearLayout>

    </LinearLayout>

</FrameLayout>
