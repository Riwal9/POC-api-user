package user

import org.junit.jupiter.api.*
import user.model.UserObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UserDaoTest {

    private val userDao = UserDao()

    private val userUuid = UUID.randomUUID()
    private val fakeUuid = UUID.randomUUID()
    private var format = SimpleDateFormat("yyyy-MM-dd")
    private val user = UserObject(
        userUuid.toString(),
        "lefaou",
        "riwal",
        "0606060606",
        "azeaze@aze.fr",
        "azeazeaze",
        "male",
        format.parse("10-08-1999"),
        "private"
    )

    @BeforeAll
    fun init() {
        userDao.createUser(user)
    }


    @Nested
    inner class GetUsers {
        @Test
        fun `getUsers - check if retrieved user created in beforeAll`() {
            assertTrue(userDao.getUsers().any { it.id == user.id })
        }
    }

    @Nested
    inner class GetUser {

        @Test
        fun `getUser - check if user is retrieved`() {
            assertEquals(userDao.getUser(userUuid).id, user.id)
        }

        @Test
        fun `getUser - check if not retrieved with fakeUuid`() {
            assertFailsWith<NoSuchElementException> { userDao.getUser(fakeUuid) }
        }
    }

    @Nested
    inner class UpdateUser {
        @Test
        fun `updateUser - check if user is updated`() {
            user.first_name = "Sisi"
            userDao.updateUser(user)
            assertTrue(userDao.getUser(userUuid).first_name == "Sisi")
        }
    }

    @Nested
    inner class DeleteUser {
        @Test
        fun `deleteUser - check if user created in beforeAll is deleted`() {
            assertTrue(userDao.deleteUser(userUuid) == 1)
            assertFailsWith<NoSuchElementException> { userDao.getUser(userUuid) }
        }

        @Test
        fun `deleteUser - check if fakeUuid fails`() {
            assertTrue(userDao.deleteUser(fakeUuid) == 0)
        }
    }

    @AfterAll
    fun deleteUser() {
        userDao.deleteUser(userUuid)
    }

}