# 더 건강한 학식

## 소개
문제점 : 충북대학교 학식(한빛식당)의 메뉴에는 칼로리 정보가 제공되지 않아 상당한 불편함이 있었음.<br>
앱의 목적 : 한빛 식당의 메뉴에서 주된 메뉴가 어느 정도의 칼로리를 갖는지 추정치를 제공하고자 함.

## 개발자 소개
- 이종찬 | @Pachyhead
- 이재협 | @temraire117
- 김주형 | @ImagoDei0212

## 설치방법
1. Java 설치 : 20.0.2
2. 안드로이드 스튜디오 설치
3. Android SDK 설치 : API24 ("Nougat"; Android 7.0) (안드로이드 스튜디오 처음 실행시 설정)
4. git clone https://github.com/Pachyhead/OSB_Friday.git
5. 우측 상단의 Sync Project with Gradle Files을 클릭(ctrl + shift + O) / 설정 적용에 시간이 걸릴 수 있습니다.

## 실행방법
1. ${project}\OSB_Friday_Main_UI\app\src\main\java\com\example\main_ui 내의 java 파일을 안드로이드 스튜디오로 열기
2. 안드로이드 가상 머신(AVD) 설치 : Pixel 8 --> x86 --> UpsideDownCake
3. 상단의 Run 버튼 클릭

## 의존성
#### OS(Android Studio의 경우) 
- Windows MacOs Linux

#### 메인 의존성
- **jsoup** (Version 1.17.2)

#### 테스트 의존성
- **JUnit**: 단위 테스트 프레임워크
- **JaCoCo**: Java 코드 커버리지 도구

## 라이선스
MIT License

Copyright (c) 2024 이종찬

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

---

Apache License
Version 2.0, January 2004 

http://www.apache.org/licenses/

TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION
...
(여기에 Apache 2.0 라이선스 전문을 포함합니다. Apache 2.0 라이선스 전문은 License 탭 참조)

## 사용된 라이브러리의 라이선스
- **jsoup**: MIT License. 자세한 내용은 [jsoup License](https://jsoup.org/license)를 참조하십시오.
- **Mockito**: MIT License. 자세한 내용은 [Mockito License](https://github.com/mockito/mockito/blob/main/LICENSE)를 참조하십시오.
- **JUnit**: JUnit License. 자세한 내용은 [JUnit License](https://github.com/junit-team/junit4/blob/main/LICENSE-junit.txt)를 참조하십시오.
- **JaCoCo**: Eclipse Public License 2.0. 자세한 내용은 [Eclipse Public License 2.0](https://www.eclipse.org/legal/epl-2.0/)를 참조하십시오.
