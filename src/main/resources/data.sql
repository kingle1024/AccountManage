insert into account_user(id, name, created_at, updated_at)
values(1, 'Pororo', now(), now());
insert into account_user(id, name, created_at, updated_at)
values(2, 'Lupi', now(), now());
insert into account_user(id, name, created_at, updated_at)
values(3, 'Eddie', now(), now());

-- defer-datasource-initialization : true
-- build.gralde 이게 있어야 정상 작동한다.