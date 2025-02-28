local cursor = "0"
local totalSubscriptions = 0
repeat
    local result = redis.call("SCAN", cursor, "MATCH", "mqtt:subscriptions:*", "COUNT", 1000)
    cursor = result[1]
    for _, key in ipairs(result[2]) do
        totalSubscriptions = totalSubscriptions + redis.call("SCARD", key)
    end
until cursor == "0"
return totalSubscriptions