# 3Jeon-server

# 지도 API
### 네이버 지도 경로 탐색
- GET https://threejeon.shop/map/?start-lat=37.359708&start-lng=127.1058342&goal-lat=35.179470&goal-lng=129.075986

# 매장 API
### Categorys : "1인분", "한식", "일식", "중식", "양식", "치킨", "분식", "고기구이", "도시락", "야식", "패스트푸드", "디저트", "아시안푸드"  
### Sort : "rank", "review_avg", "review_count", "distance"  
## 요기요
### 현위치 주변 매장 조회(카테고리: 패스트푸드)
GET https://threejeon.shop/yogiyo/restaurant?lat=37.54775437002297&lng=127.06083008344&category=패스트푸드&sort=distance

### 가게의 모든 메뉴 조회
GET https://threejeon.shop/yogiyo/313580/menu

### 매장검색(키워드: 굽네치킨, 정렬: 거리)
GET https://threejeon.shop/yogiyo/search-restaurants?lat=37.5479009261467&lng=127.062295814953&search=굽네치킨&sort=distance

## 배달의 민족
### 현위치 주변 매장 조회(카테고리: 고기구이, 정렬: 거리)
GET https://threejeon.shop/baemin/restaurant?lat=37.5479009261467&lng=127.062295814953&category=고기구이&sort=distance

### 가게의 모든 메뉴 조회
GET https://threejeon.shop/baemin/13225239/menu

### 매장 검색(키워드: 굽네치킨, 정렬: 랭크)
GET https://threejeon.shop/baemin/search-restaurants?lat=37.5479009261467&lng=127.062295814953&search=굽네치킨&sort=rank

## 쿠팡이츠
### 현위치 주변 매장 조회(카테고리: 도시락, 리뷰점수)
GET https://threejeon.shop/coupang/restaurant?lat=37.5479009261467&lng=127.062295814953&category=도시락&sort=review_avg

### 가게의 모든 메뉴 조회
GET https://threejeon.shop/coupang/469509/menu

### 매장 검색(키워드: 굽네치킨, 정렬: 리뷰개수)
GET https://threejeon.shop/coupang/search-restaurants?lat=37.5479009261467&lng=127.062295814953&search=굽네치킨&sort=review_count

# 로그인 API
## 전체 로그인 페이지
https://threejeon.shop/login  

### 카카오톡 로그인
https://threejeon.shop/oauth2/authorization/kakao

### 구글 로그인
https://threejeon.shop/oauth2/authorization/google

### 네이버 로그인
https://threejeon.shop/oauth2/authorization/naver
