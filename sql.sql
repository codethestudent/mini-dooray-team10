CREATE TABLE `project`
(
    `project_id`          INTEGER      NOT NULL DEFAULT Auto_increment COMMENT '프로젝트 아이디',
    `project_name`        VARCHAR(255) NOT NULL COMMENT '프로젝트 제목',
    `project_description` VARCHAR(255) NULL COMMENT '프로젝트 설명',
    `admin_id`            VARCHAR(50)  NOT NULL COMMENT '관리자 아이디',
    `project_state`       VARCHAR(10)  NOT NULL DEFAULT 활성 COMMENT '프로젝트 상태'
);

CREATE TABLE `task`
(
    `task_id`      INTEGER      NOT NULL DEFAULT Auto_increment COMMENT '업무 아이디',
    `task_name`    VARCHAR(255) NOT NULL COMMENT '업무 제목',
    `dead_line`    Date         NULL COMMENT '만기일',
    `content`      VARCHAR(255) NULL COMMENT '업무 내용',
    `milestone_id` INTEGER      NULL     DEFAULT NULL COMMENT '1:1관계',
    `project_id`   INTEGER      NOT NULL DEFAULT Auto_increment COMMENT '프로젝트 아이디',
    `user_id`      VARCHAR(50)  NULL COMMENT '등록자'
);

CREATE TABLE `tag`
(
    `tag_id`     INTEGER      NOT NULL DEFAULT Auto_increment COMMENT '태그 아이디',
    `tag_name`   VARCHAR(255) NOT NULL COMMENT '태그 이름',
    `project_id` INTEGER      NOT NULL COMMENT '프로젝트 아이디'
);

CREATE TABLE `member`
(
    `user_id`       varchar(50)  NOT NULL COMMENT '회원 아이디',
    `user_password` varchar(255) NULL COMMENT '회원 비밀번호',
    `user_email`    varchar(255) NULL COMMENT '회원 이메일',
    `user_state`    varchar(20)  NOT NULL COMMENT 'ENUM'
);

CREATE TABLE `comment`
(
    `comment_id`   INTEGER      NOT NULL DEFAULT Auto_increment COMMENT '댓글 아이디',
    `content`      VARCHAR(255) NULL COMMENT '댓글 내용',
    `created_date` DATETIME     NOT NULL COMMENT '댓글 생성일',
    `task_id`      INTEGER      NOT NULL COMMENT 'auto_increment,업무 아이디',
    `user_id`      VARCHAR(50)  NOT NULL
);

CREATE TABLE `project_member`
(
    `user_id`    VARCHAR(50) NOT NULL COMMENT '프로젝트 멤버 회원',
    `project_id` INTEGER     NOT NULL COMMENT '프로젝트 아이디'
);

CREATE TABLE `milestone`
(
    `milestone_id`   INTEGER      NOT NULL COMMENT 'auto_increment',
    `milestone_name` VARCHAR(255) NULL COMMENT '단계명',
    `start_date`     DATETIME     NULL COMMENT '시작시점',
    `end_date`       DATETIME     NULL COMMENT '종료시점',
    `project_id`     INTEGER      NOT NULL DEFAULT Auto_increment COMMENT '프로젝트 아이디'
);

CREATE TABLE `task_tag`
(
    `tag_id`  INTEGER NOT NULL DEFAULT Auto_increment COMMENT '태그 아이디',
    `task_id` INTEGER NOT NULL DEFAULT Auto_increment COMMENT '업무 아이디'
);

ALTER TABLE `project`
    ADD CONSTRAINT `PK_PROJECT` PRIMARY KEY (
                                             `project_id`
        );

ALTER TABLE `task`
    ADD CONSTRAINT `PK_TASK` PRIMARY KEY (
                                          `task_id`
        );

ALTER TABLE `tag`
    ADD CONSTRAINT `PK_TAG` PRIMARY KEY (
                                         `tag_id`
        );

ALTER TABLE `member`
    ADD CONSTRAINT `PK_MEMBER` PRIMARY KEY (
                                            `user_id`
        );

ALTER TABLE `comment`
    ADD CONSTRAINT `PK_COMMENT` PRIMARY KEY (
                                             `comment_id`
        );

ALTER TABLE `project_member`
    ADD CONSTRAINT `PK_PROJECT_MEMBER` PRIMARY KEY (
                                                    `user_id`,
                                                    `project_id`
        );

ALTER TABLE `milestone`
    ADD CONSTRAINT `PK_MILESTONE` PRIMARY KEY (
                                               `milestone_id`
        );

ALTER TABLE `task_tag`
    ADD CONSTRAINT `PK_TASK_TAG` PRIMARY KEY (
                                              `tag_id`,
                                              `task_id`
        );