alter table user_story add actual_effort double null default 0;
alter table task add actual_effort double null default 0;
alter table project add calculate_story_estimates boolean not null default true;