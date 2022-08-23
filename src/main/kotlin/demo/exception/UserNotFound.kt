package demo.exception

class UserNotFound(userId: String) : Exception("User not found. Id: $userId")