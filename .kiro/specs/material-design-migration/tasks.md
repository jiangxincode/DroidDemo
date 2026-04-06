# 实施计划：Material Design 3 迁移

## 概述

按照设计文档的分层递进策略，从 Gradle 依赖升级开始，逐步完成主题系统、配色系统、排版系统、形状系统、核心 UI 组件、导航组件、第三方库替换、Edge-to-Edge 适配，最终进行向后兼容性验证。每个步骤在前一步骤基础上递增构建，确保无孤立代码。

## Tasks

- [x] 1. Gradle 依赖与构建配置升级
  - [x] 1.1 修改 `app/build.gradle`，移除 `androidx.legacy:legacy-support-v4:1.0.0` 依赖，添加 `androidx.viewpager2:viewpager2` 依赖，移除 `com.github.JakeWharton:ViewPagerIndicator:2.4.1`、`com.jeremyfeinstein.slidingmenu:library:1.3@aar`、`com.astuetz:pagerslidingtabstrip:1.0.1` 三个第三方库依赖
    - 确认 `com.google.android.material:material:1.13.0` 已满足 MD3 要求，无需升级版本
    - 保留 `compileSdk 36` 和 `minSdkVersion 34`
    - _需求：1.1, 1.2, 1.3, 9.4_

  - [ ]* 1.2 编写单元测试验证构建配置
    - 解析 `build.gradle` 文件，验证 legacy-support-v4 已移除、viewpager2 已添加、旧三方库已移除
    - _需求：1.2, 1.4, 9.4_

- [x] 2. 主题系统迁移与 Dynamic Color 启用
  - [x] 2.1 修改 `res/values/styles.xml`，将三个主题的父主题从 AppCompat 切换为 Material3
    - `AppTheme.NoActionBar`：`Theme.AppCompat.DayNight.NoActionBar` → `Theme.Material3.DayNight.NoActionBar`
    - `AppTheme.ActionBar`：`Theme.AppCompat.DayNight` → `Theme.Material3.DayNight`
    - `TvTheme`：`Theme.AppCompat.NoActionBar` → `Theme.Material3.DayNight.NoActionBar`
    - 在每个主题中定义完整的 MD3 色彩角色属性和形状属性
    - _需求：2.1, 2.2, 2.3, 2.4, 7.1_

  - [x] 2.2 修改 `ApplicationExt.java`，在 `onCreate()` 中添加 `DynamicColors.applyToActivitiesIfAvailable(this)` 调用
    - _需求：2.5_

  - [ ]* 2.3 编写属性测试验证主题完整性
    - **属性 1：主题完整性**
    - 对于任意应用主题，验证所有必需的 MD3 色彩角色属性和形状属性均已定义
    - **验证需求：2.4, 7.1**

- [x] 3. 配色系统重构
  - [x] 3.1 修改 `res/values/colors.xml`，新增完整的 MD3 色调调色板
    - 定义 Primary、Secondary、Tertiary 三组色调及其 Container 和 On 变体
    - 定义 Surface、SurfaceVariant、OnSurface、OnSurfaceVariant、Outline、OutlineVariant
    - 定义 Error、OnError、ErrorContainer、OnErrorContainer
    - 移除旧的 `colorPrimaryDark` 和 `colorAccent` 定义
    - _需求：3.1, 3.2, 3.3, 3.4_

  - [x] 3.2 更新所有布局文件中对旧颜色资源（`colorPrimaryDark`、`colorAccent`）的引用，替换为对应的 MD3 色彩角色
    - _需求：3.5_

  - [ ]* 3.3 编写属性测试验证配色系统完整性
    - **属性 2：配色系统完整性**
    - 对于任意 MD3 所需色彩角色，验证 `colors.xml` 中有对应定义
    - **验证需求：3.1, 3.2, 3.3**

  - [ ]* 3.4 编写属性测试验证布局文件无旧色彩引用
    - **属性 3：布局文件无旧色彩引用**
    - 对于任意布局 XML 文件，验证不包含对 `colorPrimaryDark`、`colorAccent` 的引用
    - **验证需求：3.5**

- [x] 4. 排版系统更新
  - [x] 4.1 在 `res/values/styles.xml` 中定义 MD3 排版样式覆盖（如需自定义字体）
    - 确保 displayLarge 至 labelSmall 共 15 个排版样式可用（通过 MD3 主题继承或显式定义）
    - _需求：4.1_

  - [x] 4.2 更新布局文件，将硬编码的 `android:textSize` 替换为 `android:textAppearance` 引用 MD3 排版样式
    - 如 `?attr/textAppearanceBodyLarge`、`?attr/textAppearanceTitleMedium` 等
    - _需求：4.2, 4.3_

  - [ ]* 4.3 编写属性测试验证排版系统完整性
    - **属性 4：排版系统完整性**
    - 对于任意 MD3 所需排版样式名称，验证定义存在或可通过主题继承获得
    - **验证需求：4.1**

  - [ ]* 4.4 编写属性测试验证布局文件无硬编码文字大小
    - **属性 5：布局文件无硬编码文字大小**
    - 对于任意布局 XML 文件中的 TextView 或其子类，验证不使用硬编码 `android:textSize`
    - **验证需求：4.2, 4.3**

- [x] 5. 检查点 - 基础层与主题层验证
  - 确保项目编译通过，所有主题、配色、排版、形状系统配置正确。如有问题请向用户确认。

- [x] 6. 核心 UI 组件迁移
  - [x] 6.1 将布局文件中的 `Button` 替换为 `MaterialButton`，根据用途选择 Filled、Outlined、Text、Tonal 样式
    - 涉及 `content_main.xml` 等多个布局文件
    - _需求：5.1_

  - [x] 6.2 将 `androidx.cardview.widget.CardView` 替换为 `com.google.android.material.card.MaterialCardView`
    - 涉及 `activity_card_view.xml` 等布局文件
    - 应用 MD3 卡片样式（Filled、Elevated、Outlined）
    - _需求：5.2_

  - [x] 6.3 将 Java 代码中的 `AlertDialog.Builder` 替换为 `MaterialAlertDialogBuilder`
    - 更新所有对话框构建代码
    - _需求：5.3_

  - [x] 6.4 将布局文件中的 `Toolbar` / `ActionBar` 替换为 `MaterialToolbar`
    - 使用 MD3 TopAppBar 样式
    - _需求：5.4_

  - [x] 6.5 将 `EditText` 替换为 `TextInputLayout` + `TextInputEditText`，使用 MD3 Filled 或 Outlined 样式
    - _需求：5.5_

  - [x] 6.6 将 `Spinner` 替换为 `TextInputLayout` + `MaterialAutoCompleteTextView`（ExposedDropdownMenu 样式）
    - 涉及 `activity_spinner.xml`
    - _需求：5.6_

  - [x] 6.7 将 `Switch` / `CheckBox` 替换为 `MaterialSwitch` / `MaterialCheckBox`
    - _需求：5.7_

  - [ ]* 6.8 编写属性测试验证布局组件迁移完整性
    - **属性 6：布局组件迁移完整性**
    - 对于任意布局 XML 文件，验证不包含已废弃组件标签（非 Material 的 Button、CardView、Spinner、Switch、CheckBox、EditText）
    - **验证需求：5.1, 5.2, 5.5, 5.6, 5.7**

  - [ ]* 6.9 编写属性测试验证 Java 代码组件迁移完整性
    - **属性 7：Java 代码组件迁移完整性**
    - 对于任意 Java 源文件，验证不包含 `AlertDialog.Builder` 的直接使用；布局文件中不包含 `<androidx.appcompat.widget.Toolbar`
    - **验证需求：5.3, 5.4**

- [x] 7. 形状系统应用
  - [x] 7.1 在 `res/values/styles.xml` 中定义 `ShapeAppearance.App.SmallComponent`、`ShapeAppearance.App.MediumComponent`、`ShapeAppearance.App.LargeComponent` 样式
    - 在主题中通过 `shapeAppearanceSmallComponent` 等属性引用
    - _需求：7.1, 7.3_

  - [x] 7.2 更新布局文件，将硬编码的 `app:cardCornerRadius`、`android:radius` 等圆角值替换为 MD3 形状系统引用
    - _需求：7.2_

  - [ ]* 7.3 编写属性测试验证布局文件无硬编码圆角值
    - **属性 9：布局文件无硬编码圆角值**
    - 对于任意布局 XML 文件，验证不包含硬编码的 `app:cardCornerRadius` 或 `android:radius`
    - **验证需求：7.2**

- [x] 8. 导航组件现代化
  - [x] 8.1 修改 `res/layout/activity_main.xml`，将 `androidx.viewpager.widget.ViewPager` 替换为 `androidx.viewpager2.widget.ViewPager2`
    - _需求：6.1_

  - [x] 8.2 创建 `MainActivityFragmentStateAdapter.java`，替代现有的 `MainActivityViewPagerAdapter.java`（FragmentPagerAdapter → FragmentStateAdapter）
    - 基于 `FragmentActivity` 构造
    - 实现 `createFragment()` 和 `getItemCount()`
    - _需求：6.5_

  - [x] 8.3 修改 `MainActivity.java`，将 ViewPager 替换为 ViewPager2，将 `setOnNavigationItemSelectedListener` 替换为 `setOnItemSelectedListener`
    - 注册 `OnPageChangeCallback` 实现 ViewPager2 与 BottomNavigationView 的双向同步
    - 为 BottomNavigationView 应用 MD3 NavigationBar 样式（含指示器效果）
    - _需求：6.1, 6.2, 6.3, 6.4_

  - [ ]* 8.4 编写属性测试验证导航标签与页面同步
    - **属性 8：导航标签与页面同步**
    - 对于任意有效标签页索引（0、1、2），验证 BottomNavigationView 选择与 ViewPager2 页面双向同步
    - **验证需求：6.4**

- [x] 9. 第三方库替换
  - [x] 9.1 将使用 `ViewPagerIndicator` 的布局和 Java 代码替换为 `TabLayout` + `ViewPager2` + `TabLayoutMediator`
    - 更新布局 XML 和对应 Java 文件中的初始化与事件监听逻辑
    - _需求：9.4_

  - [x] 9.2 将使用 `PagerSlidingTabStrip` 的布局和 Java 代码替换为 `TabLayout` + `ViewPager2` + `TabLayoutMediator`
    - _需求：9.4_

  - [x] 9.3 将使用 `SlidingMenu` 的 Activity 布局重构为 `DrawerLayout` + `NavigationView`
    - 更新布局 XML 为 DrawerLayout 包裹 NavigationView 的标准结构
    - 更新 Java 代码中的初始化和事件监听逻辑
    - _需求：9.4_

  - [ ]* 9.4 编写属性测试验证第三方库完全替换
    - **属性 10：第三方库完全替换**
    - 对于任意 Java 源文件和布局 XML 文件，验证不包含对 ViewPagerIndicator、SlidingMenu、PagerSlidingTabStrip 的导入或组件引用
    - **验证需求：9.4**

- [x] 10. Edge-to-Edge 显示适配
  - [x] 10.1 在 `MainActivity.java` 的 `onCreate()` 中添加 `WindowCompat.setDecorFitsSystemWindows(getWindow(), false)`
    - _需求：8.1_

  - [x] 10.2 使用 `WindowInsetsCompat` 为根布局和 BottomNavigationView 设置系统栏内边距处理
    - 确保 BottomNavigationView 不被系统导航栏遮挡
    - 确保滚动内容不被状态栏遮挡
    - _需求：8.2, 8.3, 8.4_

- [x] 11. 检查点 - 组件层与导航层验证
  - 确保项目编译通过，所有 UI 组件迁移、导航现代化、第三方库替换、Edge-to-Edge 适配均正常工作。如有问题请向用户确认。

- [x] 12. 向后兼容性验证
  - [x] 12.1 验证项目编译成功且无编译错误和资源引用错误
    - _需求：9.1_

  - [x] 12.2 验证所有现有 Activity 和自定义 View 功能不受迁移影响
    - 检查 AndroidManifest.xml 中注册的所有 Activity 的主题引用正确
    - _需求：9.2, 9.3_

  - [ ]* 12.3 运行现有 Instrumented Test 确保全部通过
    - _需求：9.5_

- [x] 13. 最终检查点 - 全面验证
  - 确保所有测试通过，所有需求已覆盖。如有问题请向用户确认。

## 备注

- 标记 `*` 的子任务为可选任务，可跳过以加速 MVP 交付
- 每个任务引用了具体的需求编号，确保可追溯性
- 检查点任务用于阶段性验证，确保增量式迁移的正确性
- 属性测试使用 jqwik 库，验证设计文档中定义的通用正确性属性
- 单元测试验证具体示例和边界情况
