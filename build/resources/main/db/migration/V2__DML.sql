INSERT INTO public.authority VALUES ('4da5473b-09b4-4941-9556-e75f439ccc39', 'USER_CREATE');
INSERT INTO public.authority VALUES ('d8ced773-78b5-41c8-9c0a-0482de29b51f', 'USER_MODIFY');
INSERT INTO public.authority VALUES ('dfd2440e-eddf-488a-b4a7-225b6e21a484', 'USER_SEE');
INSERT INTO public.authority VALUES ('c40a573a-a6bd-413f-b573-64338770e261', 'USER_DELETE');


INSERT INTO public.role VALUES ('7ea6108f-6163-46ae-8b1f-1adbb1daaef6', 'ADMIN_USER');
INSERT INTO public.role VALUES ('a807f074-53bc-4aa7-a6d2-341928b7dd2a', 'BASIC_USER');


INSERT INTO public.role_authority VALUES ('7ea6108f-6163-46ae-8b1f-1adbb1daaef6', '4da5473b-09b4-4941-9556-e75f439ccc39');
INSERT INTO public.role_authority VALUES ('7ea6108f-6163-46ae-8b1f-1adbb1daaef6', 'd8ced773-78b5-41c8-9c0a-0482de29b51f');
INSERT INTO public.role_authority VALUES ('7ea6108f-6163-46ae-8b1f-1adbb1daaef6', 'c40a573a-a6bd-413f-b573-64338770e261');
INSERT INTO public.role_authority VALUES ('7ea6108f-6163-46ae-8b1f-1adbb1daaef6', 'dfd2440e-eddf-488a-b4a7-225b6e21a484');
INSERT INTO public.role_authority VALUES ('a807f074-53bc-4aa7-a6d2-341928b7dd2a', 'dfd2440e-eddf-488a-b4a7-225b6e21a484');


INSERT INTO public.users VALUES ('2643dbc0-cd29-4446-84cb-0c98aed89fb8', '2020-12-12', '2020-12-12', 'john.doe@noseryoung.ch', 1, 'John', 'Doe', 0, '$2a$10$XKIRmT.3a4FqYIsFE88opOtNBbzmTxEKSWhsBoT1iUZhtgYqNCS.O');
INSERT INTO public.users VALUES ('183bd877-f871-4000-b9c7-a7ce92991b5f', '2020-12-12', '2020-12-12', 'jane.doe@noseryoung.ch', 1, 'Jane', 'Doe', 0, '$2a$10$XKIRmT.3a4FqYIsFE88opOtNBbzmTxEKSWhsBoT1iUZhtgYqNCS.O');


INSERT INTO public.users_role VALUES ('2643dbc0-cd29-4446-84cb-0c98aed89fb8', '7ea6108f-6163-46ae-8b1f-1adbb1daaef6');
INSERT INTO public.users_role VALUES ('183bd877-f871-4000-b9c7-a7ce92991b5f', 'a807f074-53bc-4aa7-a6d2-341928b7dd2a');