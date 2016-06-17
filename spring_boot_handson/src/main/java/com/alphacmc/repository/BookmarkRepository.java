/**
 * 
 */
package com.alphacmc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alphacmc.domain.Bookmark;

/**
 * Bookmarkリポジトリ
 * @author matsumoto
 *
 */
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

}
