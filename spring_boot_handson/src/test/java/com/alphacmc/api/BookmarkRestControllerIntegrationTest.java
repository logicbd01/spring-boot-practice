package com.alphacmc.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.alphacmc.SpringBootHandsonApplication;
import com.alphacmc.domain.Bookmark;
import com.alphacmc.repository.BookmarkRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootHandsonApplication.class)
@WebAppConfiguration
@IntegrationTest({
	"server.port:0", 
	"spring.datasource.url:jdbc:h2:mem:bookmark;DB_CLOSE_ON_EXIT=FALSE"
})
public class BookmarkRestControllerIntegrationTest {
	// リポジトリ
	@Autowired
	BookmarkRepository bookmarkRepository;
	// ポート番号 (プロパティから読み込み)
	@Value("${local.server.port}")
	int port;
	// リクエスト先アクションパス
	String apiEndpoint;
	RestTemplate restTemplate = new TestRestTemplate();
	// テストデータ用
	Bookmark springIO;
	Bookmark springBoot;

	@Before
	public void setUp() {
		// テストメソッド実行前の共通的な試験データ準備
		// データ削除
		bookmarkRepository.deleteAll();
		// テストデータ
		springIO = new Bookmark();
		springIO.setName("Spring IO");
		springIO.setUrl("http://spring.io");
		springBoot = new Bookmark();
		springBoot.setName("Spring Boot");
		springBoot.setUrl("http://project.spirng.io/spring-boot");
		// テストデータ登録
		bookmarkRepository.save(Arrays.asList(springIO, springBoot));
		// リクエスト先URL
		apiEndpoint = "http://localhost:" + port + "/api/bookmarks";
	}

	@Test
	public void testGetBookmarks() throws Exception {
		// テスト実行
		ResponseEntity<List<Bookmark>> response = restTemplate.exchange(
			apiEndpoint, 
			HttpMethod.GET, 
			null, 
			new ParameterizedTypeReference<List<Bookmark>>(){}
		);

		// 検証
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().size(), is(2));

		Bookmark bookmark1 = response.getBody().get(0);
		assertThat(bookmark1.getId(), is(springIO.getId()));
		assertThat(bookmark1.getName(), is(springIO.getName()));
		assertThat(bookmark1.getUrl(), is(springIO.getUrl()));

		Bookmark bookmark2 = response.getBody().get(1);
		assertThat(bookmark2.getId(), is(springBoot.getId()));
		assertThat(bookmark2.getName(), is(springBoot.getName()));
		assertThat(bookmark2.getUrl(), is(springBoot.getUrl()));
	}

	@Test
	public void testPostBookmarks() throws Exception {
		// テストデータ
		Bookmark google = new Bookmark();
		google.setName("Google");
		google.setUrl("http://google.com");

		// テスト実行
		ResponseEntity<Bookmark> response = restTemplate.exchange(
			apiEndpoint, 
			HttpMethod.POST, 
			new HttpEntity<>(google), 
			Bookmark.class
		);

		// 検証
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		Bookmark bookmark = response.getBody();
		assertThat(bookmark.getId(), is(notNullValue()));
		assertThat(bookmark.getName(), is(google.getName()));
		assertThat(bookmark.getUrl(), is(google.getUrl()));
		assertThat(restTemplate.exchange(
			apiEndpoint, 
			HttpMethod.GET, 
			null, 
			new ParameterizedTypeReference<List<Bookmark>>(){}
			).getBody().size(), is(3));
	}

	@Test
	public void testDeleteBookmarks() throws Exception {
		// テスト実行
		ResponseEntity<Void> response = restTemplate.exchange(
			apiEndpoint + "/{id}", 
			HttpMethod.DELETE, 
			null, 
			Void.class, 
			Collections.singletonMap("id", springIO.getId())
			);

		// 検証
		assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
		assertThat(restTemplate.exchange(
			apiEndpoint, 
			HttpMethod.GET, 
			null, 
			new ParameterizedTypeReference<List<Bookmark>>(){}
			).getBody().size(), is(1)
		);
	}
}