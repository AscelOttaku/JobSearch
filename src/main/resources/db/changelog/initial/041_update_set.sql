update MESSAGES
set MESSAGE_OWNER_ID = (select USER_ID from USERS
    where FIRST_NAME like 'Sam')
where MESSAGE_OWNER_ID is null