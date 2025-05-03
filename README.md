# 이미지/동영상 콘텐츠 썸네일 수집 앱
> **검색하고, 담고, 나만의 보관함에 저장하는 콘텐츠 큐레이션 앱**

## 프로젝트 개요
카카오 이미지/동영상 검색 API를 활용해 키워드 기반의 콘텐츠를 검색하고,   
사용자가 원하는 썸네일 이미지를 보관함에 수집할 수 있는 안드로이드 앱입니다.

## 주요 기능
- **이미지/동영상 통합 검색**  
    - API
        - [카카오 이미지 검색 API](https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-image)
        - [카카오 동영상 검색 API](https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-video)
  - 키워드 입력 시 검색 결과 제공

- **썸네일 기반 결과 제공**  
  - `LazyVerticalGrid`를 활용한 썸네일 리스트 제공  
  - Paging3 기반으로 무한 스크롤 및 로딩 최적화

- **즐겨찾기(보관함) 기능**  
  - 사용자가 원하는 콘텐츠를 로컬 DB에 저장하여 ‘보관함’에 수집

- **UX 최적화**  
  - 검색 후 키보드 자동 숨김, 로딩/에러/빈 상태 UI 명확하게 분리  
  - 상태에 따른 안내 문구 및 터치 영역 최적화
  - 폴더블 및 대형 화면 대응을 위한 유연한 레이아웃 구성

</br>

## 프로젝트 구조
멀티 모듈 + Clean Architecture 기반으로 기능 단위와 역할 단위로 모듈을 분리하여 유지보수성을 높였습니다.
- **Architecture**: MVVM + Clean Architecture + 멀티 모듈 구성
- **UI Framework**: Jetpack Compose  
  - 상태 관리: `remember`, `mutableStateOf`, `collectAsState`, `LaunchedEffect`
  - 동적 UI: `LazyPagingItems`, `LoadState` 대응
  - **디바이스 대응**: FoldingFeature, screenWidthDp, 모델명 기반(SM-F7)으로 폴더블/태블릿 UI 최적화
- **비동기 처리**: Kotlin Flow + Coroutine
- **DI (Dependency Injection)**: Hilt
- **Networking**: Retrofit + Gson
- **로컬 저장소**: Room DB (북마크 저장)
- **API**: Kakao 이미지/동영상 검색 API

### Android
- Minimum SDK level 24
- MVVM pattern, UDF

### 디렉토리 트리
~~~nginx
📦 app
📦 core
├─ 📂 data
│  ├─ 📁 api            # API 응답 처리
│  ├─ 📁 di             # DI(Hilt) 설정
│  ├─ 📁 paging         # PagingSource
│  ├─ 📁 repository     # Repository 구현체
│  └─ 📁 room           # Room DAO 및 DB 설정
├─ 📂 model             # API 응답 DTO, 로컬 DB Entity 정의
├─ 📂 network
│  ├─ 📁 constant       # API 베이스 URL 등 상수
│  ├─ 📁 di             # 네트워크 DI 모듈
│  └─ 📁 intercepter    # 인터셉터 구성
└─ 📂 ui
   ├─ 📁 designsystem   # 재사용 가능한 디자인 시스템 컴포넌트
   ├─ 📁 extension      # Extension 함수
   ├─ 📁 theme          # Material Theme 정의
   └─ 📁 util           # UI 관련 유틸 함수
📦 domain               
├─ 📁 repository        # Repository 인터페이스
└─ 📁 model             # domain 모델
📦 feature
└─ 📁 main              # 검색, 보관함
~~~

</br>

## 참고 블로그
- https://proandroiddev.com/loading-initial-data-in-launchedeffect-vs-viewmodel-f1747c20ce62
- https://developer.android.com/develop/ui/compose/state
