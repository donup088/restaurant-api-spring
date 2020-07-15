### restaurant-api-spring

##### restaurant- 목록,상세 
- RestaurantServiceTest 에서 repository들을 생성자에서 받는 이유: Test에서 어떤 repository를 사용하는 지 모르기 때문에 의존성을 넣어주기 위해 생성자에서 어떤 repository를 사용하는 지 알려줌
- @SpyBean 사용 : ControllerTest에서 Repository에서 구현체들이 어떤 것인지 명시, Service 객체를 명시 의존성 주입을 해주는 과정  ex) @SpyBean(RestaurantService.class)
- @Autowired 사용 : Spring에서 Bean으로 등록된 객체중 사용하려는 타입의 객체를 자동으로 생성해준다.
- @Component : Spring(IOC) Container에 Bean을 등록
---
#### restaurant - 추가, JPA 사용
- repository interface에 extends CrudRepository<컬럼명,주요키>를 추가하면서 쿼리를 직접 구현하지 않고 DB에 접근이 수월해짐
- @Transient 사용: DB에 저장되지 않고 넘어가도록 할 수 있음
- ResponseEntity<?> 사용: POST로 요청을보낼 때 상태를 보내줄 수 있음. ex) URI location=new URI("/restaurants/"+restaurant.getId());
 return ResponseEntity.created(location).body("{}"); 테스트쪽에서 status().isCreated()로 확인가능
- Post로 요청을 보내면 응답부분에선 header 부분에 location이 추가된다. Test 에선 (header().string("location","/restaurants/12")) 이렇게 확인가능하다.
- @RequestBody 사용: Post 방식으로 전달된 Http 요청 데이터의 body 를 읽어옴. 사용할 때 기본생성자를 만들어줘야한다.
---
### restaurant - 수정 
- verify 사용: 기능이 사용이 되는 지 확인할 때 테스트에서 사용 ex)verify(restaurantService).updateRestaurant(333L,"Sea food","강남"); restaurantService에서 updateRestaurant이 사용되었는지 확인
- update 의 기본적인 순서 ->1.update 될 데이터를 받아온다. 2. update 할 대상을 구한다. 3.model 에서 직접 데이터를 바꿔준다. 4.update 된 객체를 return 한다.
- findBy~ JPA 사용 Optional로 나오기 때문에 그에 맞는 처리가 필요하다. 
- @Transactional 사용: 코드안에서 변경된 데이터들이 코드를 벗어날 때 DB에 적용된다.
--- 
### restaurat - 유효성검사
- spring 2.3.0 version 부터는 validation이 starter-web에 포함되어 있지 않으므로 의존성을 따로 넣어주어야한다.->bulid.gradle에 implementation 'org.springframework.boot:spring-boot-starter-validation'추가
- Test 에서 에외 생긴 것을 확인하는 방법 : 
 assertThatThrownBy(() -> {
             restaurantService.getRestaurant(10000L);
         }).isInstanceOf(RestaurantNotFoundException.class); 이런식식으로 발생한 에러확인이 가능하다.
- @ControllerAdvice 사용 : 예외처리를 한번에 할 수 있음.  
  1. @ResponseStatus(HttpStatus.NOT_FOUND)를 사용하여 요청시 에러가 발생했을 때 status 를 지정할 수 있음.
  2. @ExceptionHandler(RestaurantNotFoundException.class)사용으로 에러가 발생하는 클래스를 지정하여 다둘 수 있음.
---
### restaurant - 메뉴 관리
- Test에서 해당하는 함수를 몇번 실행했는 지 알고 싶을 때 verify(menuItemRepository,times(2)).save(any()) 같이 사용가능.
- @JsonInclude(JsonInclude.Include.NON_NULL) 사용 : 넘겨주는 json 데이터가 없는 경우 표시되지 않음.
- Service Test에서 service 객체의 의존성주입을 위해 사용하는 repository 를 생성자에 넣어줌.
- json 데이터를 넣어주는 파일을 따로 만들어서 요청 데이터에 그 파일에 있는 데이터를 넣어줌. ex) destory 값이 true일 경우 삭제하도록 만들기 위해 json 파일에 삭제하려는 아이디의 destroy 값을 true로 넣어줌.
---
### restaurant - project 분리
- project 안에서 다른 project 를 사용하려고할 때 
  1. build.gradle 에 bootJar { enabled = false } jar { enabled = true } 를 추가해준다.
  2. build.gradle 에 dependency 부분에 implementation project(':restaurant-common') 이런 식으로 다른 project를 사용한다는 의존성을 추가해준다. 
---
### data 영속화
- resources 폴더안에 .yml파일을 만들고 설정을 추가한다.
  1. spring:
       datasource:
         url: jdbc:h2:C:\Users\Administrator\Desktop\restaurant-api\data\restaurant-api
       jpa:
         hibernate:
           ddl-auto: update 
     코드를 추가하여서 datasource를 설정한다.
  2. test에서 따로 data를 사용하기 위해서
  spring:
    profiles: test
    datasource:
      url: jdbc:h2:mem:test 코드를 추가한다.
      인텔리제이 테스트에서 전체 테스트를 하기 위해서는 환경변수에 SPRING_PROFILES_ACTIVE=test 를 추가해준다.
---
### restaurant을 category와 region에 따라 구분하여 보여줌
-  @RequestParam 사용: url에서 /restaurants?region=서울 이렇게 보낼 때 요청으로 보낸 region의 값을 사용하기 위해서 @RequestParam("region") String region 과 같이 사용가능하다.
- category와 region에 따라 restaurant을 보여주기 위해서 findAllByAddressContainingAndCategoryId(region,categoryId) 와 같은 jpa 사용, 주소에 포함되어 있는 restaurant을 찾기 위해서 findAllByAddressContaining(region) 사용가능
- url로 category에 대한 정보도 보내주기 위해 /restaurant?region=서울&category=1 다음과 같이 작성
- categoryId에 해당하는 category를 미리 설정해놓고 사용하듯이 region도 address로 사용하지 않고 Id로 미리 설정하여 사용가능.
- categoryId를 사용하기 위해 restaurant domain에 categoryId를 추가해 NotNull로 만들경우 restaurant에 관련된 테스트를 바꿔야함.(에러 발생)
---
### 사용자관리(admin,deactivate,user)
- user의 level을 어떻게 주느냐에 따라서 권한이 바뀌도록 설정가능
-  Optional로 처리해야할 때 return하는 부분에 Optional.of()를 사용해도 된다. ex)given(userRepository.findById(123L)).willReturn(Optional.of(mockUser))
---
### 회원가입(암호화)
- Spring security 사용하기 위해 의존성 추가
- Spring security 관련 설정
  1. WebSecurityConfigurerAdapter 상속받아서 사용, configure함수를 오버라이딩해서 사용한다.(HttpSecurity를 인자로 받음)
  2. httpSecurity.csrf().disable()
                 .csrf().disable()//h2 console을 볼 수 있도록 함
                 .formLogin().disable()//security에서 기본으로 제공되는 로그인 폼을 없앰.
                 .headers().frameOptions().disable();//h2 console를 볼 수 있도록 설정
- password 암호화
  1.BCryptPasswordEncoder 사용 : PasswordEncoder를 인터페이스로 갖고 있기 때문에   PasswordEncoder passwordEncoder= new BCryptPasswordEncoder() 이렇게 객체를 만들어서 사용가능.
  2. 객체를 만든 뒤에 encode 함수를 사용하여 password를 암호함. ex) String encodedPassword=passwordEncoder.encode(password)
- isPresent() 사용: 동일한 email을 갖고 회원가입을 하지 않도록 막기위해서 findByEmail로 user를 찾고 해당하는 user가 있다면 예외를 발생시키는 과정에서 해당하는 user가 존재하는지 확인하기 위해 user.isPresent() 를 사용한다.
---
# 인증(AccessToken을 활용한 인증)
- SessionController에서 인증이되면 ResponseEntity.isCreated().body("{}")에서 body에 accessToken을 전달하여 user가 인증되도록함.
- UserService에서 email을 통해 user를 가져와서 password가 일치하는 확인하기 위해서 passwordEncoder.matches(password,user.getPassword()를 사용한다. 
  PasswordEncoder를 test에서 사용하기 위해서 UserService에 의존성을 갖도록 생성자에 추가해준다. 그리고 PasswordEncorder의 bean을 생성하기 위해서 @Bean을 사용하여 BCryptPasswordEncoder 객체를 return 하도록한다.
-Dto클래스는 데이터 전달만을 목적으로 만들어진 클래스 이기때문에 @Data 사용(일반적으로 Request,Response 2개 부분으로 나누어 사용)
---
# jwt 사용
- String token= Jwts.builder()
                  .claim("userId",id)
                  .claim("name",name)
                  .signWith(key, SignatureAlgorithm.HS256)
                  .compact();
   1. claim 설정
   2. signWith로 key 설정 ex)Keys.hmacShaKeyFor(secret.getBytes())->자신이 설정한 secret이라는 문자열을 byte로 바꾸어 key르르 설정
   3. key는 Key 라는 interface가 있어서 그것을 사용.
- @Value 사용 : .yml파일에 있는 것을 사용할 수 있다. ex) @Value("${jwt.secret}")
