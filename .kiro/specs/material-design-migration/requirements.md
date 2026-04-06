# 需求文档：Material Design 3 迁移

## 简介

将 DroidDemo 项目从当前基于 AppCompat / Material Design 1 的主题和组件体系，迁移至 Google 最新推荐的 Material Design 3（Material You）设计规范。迁移范围涵盖 Gradle 依赖升级、主题与配色系统重构、排版系统更新、UI 组件替换、导航模式现代化，以及向后兼容性保障。

## 术语表

- **App**：DroidDemo Android 应用程序（包名 `edu.jiangxin.droiddemo`）
- **MD3_Theme_System**：基于 Material Design 3 规范的主题系统，包含动态取色（Dynamic Color）、色调调色板（Tonal Palette）和色彩角色（Color Roles）
- **MD3_Color_System**：Material Design 3 的配色体系，包括 Primary、Secondary、Tertiary、Error、Surface、Outline 等色彩角色
- **MD3_Typography_System**：Material Design 3 的排版体系，包括 Display、Headline、Title、Body、Label 五个类别及其 Large/Medium/Small 变体
- **MD3_Component**：Material Design 3 规范中定义的 UI 组件（如 MaterialButton、MaterialCardView、NavigationBar、TopAppBar 等）
- **Dynamic_Color**：Android 12+ 引入的动态取色功能，可根据用户壁纸自动生成应用配色方案
- **Build_System**：项目的 Gradle 构建系统，包括 `app/build.gradle` 中的依赖声明和编译配置
- **Navigation_Component**：应用中用于页面间导航的组件，当前使用 ViewPager + BottomNavigationView 模式
- **Edge_to_Edge**：Android 现代化全屏显示模式，内容延伸至系统栏下方并正确处理窗口内边距

## 需求

### 需求 1：Gradle 依赖与构建配置升级

**用户故事：** 作为开发者，我希望项目的 Material 库依赖升级到最新版本，以便使用 Material Design 3 的全部组件和特性。

#### 验收标准

1. THE Build_System SHALL 将 `com.google.android.material:material` 依赖升级至 1.13.0 或更高的 Material3 兼容版本
2. THE Build_System SHALL 移除不再需要的 `androidx.legacy:legacy-support-v4` 依赖
3. THE Build_System SHALL 保留对 `compileSdk 36` 和 `minSdkVersion 34` 的支持
4. WHEN 依赖升级完成后，THE Build_System SHALL 成功编译项目且无编译错误

### 需求 2：主题系统迁移至 Material Design 3

**用户故事：** 作为开发者，我希望应用的主题从 AppCompat 迁移到 Material Design 3 主题体系，以便获得现代化的视觉风格和动态取色支持。

#### 验收标准

1. THE MD3_Theme_System SHALL 将 `AppTheme.NoActionBar` 的父主题从 `Theme.AppCompat.DayNight.NoActionBar` 替换为 `Theme.Material3.DayNight.NoActionBar`
2. THE MD3_Theme_System SHALL 将 `AppTheme.ActionBar` 的父主题从 `Theme.AppCompat.DayNight` 替换为 `Theme.Material3.DayNight`
3. THE MD3_Theme_System SHALL 将 `TvTheme` 的父主题从 `Theme.AppCompat.NoActionBar` 替换为 `Theme.Material3.DayNight.NoActionBar`
4. THE MD3_Theme_System SHALL 在所有主题中定义完整的 Material Design 3 色彩角色属性（包括 colorPrimary、colorOnPrimary、colorPrimaryContainer、colorOnPrimaryContainer、colorSecondary、colorOnSecondary、colorSecondaryContainer、colorOnSecondaryContainer、colorTertiary、colorSurface、colorOnSurface、colorSurfaceVariant、colorOutline）
5. WHEN 应用运行在 Android 12 及以上设备上时，THE MD3_Theme_System SHALL 启用 Dynamic Color 以根据用户壁纸自动生成配色方案

### 需求 3：配色系统重构

**用户故事：** 作为开发者，我希望应用的配色方案符合 Material Design 3 的色彩规范，以便提供一致且美观的视觉体验。

#### 验收标准

1. THE MD3_Color_System SHALL 在 `colors.xml` 中定义完整的 Material Design 3 色调调色板，包括 Primary、Secondary、Tertiary 三组色调及其对应的 Container 和 On 变体
2. THE MD3_Color_System SHALL 定义 Surface、SurfaceVariant、OnSurface、OnSurfaceVariant、Outline、OutlineVariant 等表面色彩角色
3. THE MD3_Color_System SHALL 定义 Error、OnError、ErrorContainer、OnErrorContainer 等错误状态色彩角色
4. THE MD3_Color_System SHALL 移除旧的 `colorPrimaryDark` 和 `colorAccent` 定义，替换为对应的 Material Design 3 色彩角色
5. WHEN 布局文件中引用了旧的颜色资源（如 `colorPrimaryDark`、`colorAccent`）时，THE MD3_Color_System SHALL 将引用更新为对应的 Material Design 3 色彩角色

### 需求 4：排版系统更新

**用户故事：** 作为开发者，我希望应用的文字排版遵循 Material Design 3 的 Typography 规范，以便提供清晰的信息层级和良好的可读性。

#### 验收标准

1. THE MD3_Typography_System SHALL 定义完整的 Material Design 3 排版样式，包括 displayLarge、displayMedium、displaySmall、headlineLarge、headlineMedium、headlineSmall、titleLarge、titleMedium、titleSmall、bodyLarge、bodyMedium、bodySmall、labelLarge、labelMedium、labelSmall
2. THE MD3_Typography_System SHALL 使用 `textAppearance` 属性引用 Material Design 3 排版样式（如 `?attr/textAppearanceBodyLarge`），替代硬编码的 `textSize` 和 `textColor`
3. WHEN 布局文件中存在硬编码的文字大小（如 `18sp`、`14sp`）时，THE MD3_Typography_System SHALL 将其替换为对应的 Material Design 3 排版样式引用

### 需求 5：核心 UI 组件迁移

**用户故事：** 作为开发者，我希望应用中的 UI 组件替换为 Material Design 3 对应的组件，以便获得统一的视觉风格和交互体验。

#### 验收标准

1. THE MD3_Component SHALL 将所有 `Button` 替换为 `com.google.android.material.button.MaterialButton`，并根据用途使用合适的样式（Filled、Outlined、Text、Tonal）
2. THE MD3_Component SHALL 将所有 `CardView` 替换为 `com.google.android.material.card.MaterialCardView`，并应用 Material Design 3 的卡片样式（Filled、Elevated、Outlined）
3. THE MD3_Component SHALL 将 `AlertDialog` 替换为 `com.google.android.material.dialog.MaterialAlertDialogBuilder` 构建的对话框
4. THE MD3_Component SHALL 将 `Toolbar` 或 `ActionBar` 替换为 `com.google.android.material.appbar.MaterialToolbar`，并使用 Material Design 3 的 TopAppBar 样式
5. THE MD3_Component SHALL 将 `EditText` 替换为 `com.google.android.material.textfield.TextInputLayout` 配合 `TextInputEditText`，并使用 Material Design 3 的 Filled 或 Outlined 样式
6. THE MD3_Component SHALL 将 `Spinner` 替换为 `com.google.android.material.textfield.MaterialAutoCompleteTextView` 配合 `TextInputLayout` 的 ExposedDropdownMenu 样式
7. WHEN 布局文件中使用了 `Switch` 或 `CheckBox` 时，THE MD3_Component SHALL 将其替换为 `com.google.android.material.materialswitch.MaterialSwitch` 或 `com.google.android.material.checkbox.MaterialCheckBox`

### 需求 6：导航组件现代化

**用户故事：** 作为开发者，我希望应用的导航模式升级为 Material Design 3 推荐的方式，以便提供更流畅的页面切换体验。

#### 验收标准

1. THE Navigation_Component SHALL 将 MainActivity 中的 `androidx.viewpager.widget.ViewPager` 替换为 `androidx.viewpager2.widget.ViewPager2`
2. THE Navigation_Component SHALL 将 `BottomNavigationView` 的 `setOnNavigationItemSelectedListener`（已弃用）替换为 `setOnItemSelectedListener`
3. THE Navigation_Component SHALL 为 `BottomNavigationView` 应用 Material Design 3 的 NavigationBar 样式，包括指示器（Indicator）效果
4. WHEN 用户在底部导航栏切换标签页时，THE Navigation_Component SHALL 正确同步 ViewPager2 的页面与底部导航栏的选中状态
5. THE Navigation_Component SHALL 将 `FragmentPagerAdapter`（已弃用）替换为 `FragmentStateAdapter`

### 需求 7：形状系统应用

**用户故事：** 作为开发者，我希望应用的组件形状遵循 Material Design 3 的 Shape 规范，以便提供一致的圆角和形状风格。

#### 验收标准

1. THE MD3_Theme_System SHALL 在主题中定义 Material Design 3 的形状属性（shapeAppearanceSmallComponent、shapeAppearanceMediumComponent、shapeAppearanceLargeComponent）
2. WHEN 布局文件中存在硬编码的圆角值时，THE MD3_Theme_System SHALL 将其替换为 Material Design 3 形状系统的引用
3. THE MD3_Theme_System SHALL 为卡片、按钮、对话框等组件应用符合 Material Design 3 规范的圆角样式

### 需求 8：Edge-to-Edge 显示适配

**用户故事：** 作为开发者，我希望应用支持 Edge-to-Edge 全屏显示模式，以便充分利用屏幕空间并符合 Android 现代化设计规范。

#### 验收标准

1. THE App SHALL 在 MainActivity 中调用 `WindowCompat.setDecorFitsSystemWindows(window, false)` 启用 Edge-to-Edge 模式
2. THE App SHALL 使用 `WindowInsetsCompat` 正确处理系统栏（状态栏和导航栏）的内边距
3. THE App SHALL 确保 BottomNavigationView 在 Edge-to-Edge 模式下正确显示，不被系统导航栏遮挡
4. WHEN 应用内容滚动时，THE App SHALL 确保内容不被状态栏或导航栏遮挡

### 需求 9：向后兼容性保障

**用户故事：** 作为开发者，我希望迁移后的应用在目标 SDK 范围内（minSdk 34）保持功能完整，以便所有目标用户正常使用。

#### 验收标准

1. WHEN 迁移完成后，THE App SHALL 成功编译且无编译错误和资源引用错误
2. THE App SHALL 保持所有现有 Activity 的功能不受迁移影响
3. THE App SHALL 保持所有现有自定义 View 的功能不受迁移影响
4. IF 某个第三方库组件（如 ViewPagerIndicator、SlidingMenu、PagerSlidingTabStrip）与 Material Design 3 主题不兼容，THEN THE App SHALL 使用更现代的 Material Design 3 兼容替代库进行替换（如 ViewPagerIndicator / PagerSlidingTabStrip → TabLayout + ViewPager2，SlidingMenu → DrawerLayout + NavigationView）
5. WHEN 迁移完成后，THE App SHALL 通过所有现有的 Instrumented Test
