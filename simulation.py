import math

x = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
x_1 = [1, 0, 0, 0, 0, 0, 0, 0, 0, 0]
x_2 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
x_3 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

def odd(n):
    if n==0:
        return 0
    else:
        return (3 * n +1)%10

def even(n):
    return (n/4)%10

def delta():
    sum = 0
    for a in range(0,10):
      sum+= (x_3[a]-x[a])**2
    return math.sqrt(sum)


def step():
    for a in range(0,10):
        if a % 2 == 0:
            x[int(a / 2)] += even(x_1[a])
            x[int(a / 2) + 5] += even(x_1[a])
        else:
            x[int((a*3 + 1) % 10)] += odd(x_1[a])


count=1
step()
print(count,x)
x_1[0]=0
while(delta()>0.25 and count<1001):
    x_3 = x_2
    x_2 = x_1
    x_1 = x
    x = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    step()
    count += 1
    print(count,x)
