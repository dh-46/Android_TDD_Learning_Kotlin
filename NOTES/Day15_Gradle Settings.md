# Day15 - Gradle 測試環境設定

## ProductFlavor

- 在Build Apk前選擇Build不同的版本。
- 一般最常見的是用BuildType區分debug & release
- ProductFlavor則適合用在區分測試或是不同版本上架使用
- Build版本區分讓執行測試或App程式時可以指定到不同程式。

### 範例

#### 1. 設定Gradle中的productFlavor參數

```
// 每一個flavor都必須被assign到一個dimension
// dimension 像是一個flavor group一樣
// dimensions 可以有多個
flavorDimensions "version", "event"
productFlavors {
    // Mock 版
    mock {
        // Assigns this product flavor to the "version" flavor dimension.
        // If you are using only one dimension, this property is optional,
        // and the plugin automatically assigns all the module's flavors to
        // that dimension.
        dimension "version"
        applicationIdSuffix ".mock"
        versionNameSuffix "-mock"
    }
    // 正式版
    prod {
        // Assigns this product flavor to the "version" flavor dimension.
        // If you are using only one dimension, this property is optional,
        // and the plugin automatically assigns all the module's flavors to
        // that dimension.
        dimension "event"
    }
}
```

[官方文件](https://developer.android.com/studio/build/build-variants?utm_source=android-studio#product-flavors)

#### 2. 在src下建立不同版本的package路徑

`src\{PRODUCT_FLAVOR}\java\`

例如: Build Variant切換成prod就會執行prod路徑下的class

### ProductFlavor 設定resource的String

可以在mock的版本與prod版本，取得不同的resource.string

```
productFlavors {
    mock {
        applicationIdSuffix ".mock"
        versionNameSuffix "-mock"
        // 設定resource的String
        resValue "string", "name", "from mock"
    }


    prod {
        resValue "string", "name", "from prod"
    }
}
```

## 使用sourceSets 建立一個測試時才能使用的程式碼

建立一個在測試時才會用到的測試工具類程式碼及測試才能用的資源檔，
在這個sourceSets，test、androidTest就各自指定了這兩種測試可以使用的目錄。

```
sourceSets {
    String sharedTestDir = 'src/sharedTest/java'
    String fakeJsonDir = 'src/sharedTest/fakejson'
    test {
        // 指定test可以使用的路徑
        java.srcDir sharedTestDir
        resources.srcDirs += fakeJsonDir
    }
    androidTest {
        // 指定androidTest可以使用的路徑
        java.srcDir sharedTestDir
        resources.srcDirs += fakeJsonDir
    }
}
```

回到Project目錄，建立shareTest目錄，在這裡的目錄就只有test與androidTest可以執行。

## 資料來源

[Day15 - Gradle 測試環境設定 ](https://ithelp.ithome.com.tw/articles/10222348)


