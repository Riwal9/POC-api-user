package subscription

import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import user.UserDao
import user.model.UserObject
import subscription.model.SubscriptionObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.test.assertFailsWith

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SubscriptionDAOTest {

    private val userDao = UserDao()
    private val subscriptionDAO = SubscriptionDAO()
    private val user1Uuid = UUID.randomUUID()
    private val user2Uuid = UUID.randomUUID()
    private val fakeUserUuid = UUID.randomUUID()
    private var format = SimpleDateFormat("yyyy-MM-dd")
    private var subscription = SubscriptionObject(
        user1Uuid,
        user2Uuid
    )
    private val user1 = UserObject(
        user1Uuid.toString(),
        "lefaou",
        "riwal",
        "0606060606",
        "azeaze@aze.fr",
        "azeazeaze",
        "male",
        format.parse("10-08-1999"),
        "private"
    )
    private val user2 = UserObject(
        user2Uuid.toString(),
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
    fun setUp() {
        userDao.createUser(user1)
        userDao.createUser(user2)
        subscriptionDAO.subscribe(user1Uuid, user2Uuid)
    }

    @Nested
    inner class GetSubscriptions {
        @Test
        fun getSubscriptions() {
            assertTrue(subscriptionDAO.getSubscriptions(user1Uuid).last().user == user1Uuid)
            assertTrue(subscriptionDAO.getSubscriptions(user1Uuid).last().subscribed_to == user2Uuid)
        }

        @Test
        fun getFakeUserSubscriptions() {
            assertFailsWith<NoSuchElementException> { subscriptionDAO.getSubscriptions(fakeUserUuid) }
        }
    }

    @Nested
    inner class Subscribe {
        @Test
        fun subscribeToUser() {
            subscriptionDAO.subscribe(user2Uuid, user1Uuid)
            assertTrue(subscriptionDAO.getSubscriptions(user2Uuid).last().user == user2Uuid)
            assertTrue(subscriptionDAO.getSubscriptions(user2Uuid).last().subscribed_to == user1Uuid)

        }

        @Test
        fun subscribeToFakeUser() {
            assertFailsWith<ExposedSQLException> {subscriptionDAO.subscribe(user2Uuid, fakeUserUuid)}
        }
    }

    @Nested
    inner class Unsubscribe {
        @Test
        fun unsubscribe() {
        }
    }

    @AfterAll
    fun tearDown() {
        subscriptionDAO.unsubscribe(user1Uuid, user2Uuid)
        subscriptionDAO.unsubscribe(user2Uuid, user1Uuid)
        userDao.deleteUser(user1Uuid)
        userDao.deleteUser(user2Uuid)
    }

}