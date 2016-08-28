# aidldemo
aidl 调用示例

使用 mvp 架构，展示 aidl 调用顺序

MediaPlayer 分二层封装
* PlayBinder.java 处理播放当前歌曲
* PlayManager.java 处理所有的播放歌曲的逻辑，并对 PlayBinder 进行封装调用



bug：快速多次点击上一曲，会崩溃
