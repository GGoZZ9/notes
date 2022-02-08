from functools import wraps


def test_decorator(func):  # 装饰器
    @wraps(func)
    def wrapper(*args, **kwargs):
        return func(*args, **kwargs)
    return wrapper


def the_function():
    pass


class TheClass:
    def __call__(self, *args, **kwargs):
        return self

    @classmethod
    def class_method(cls):
        pass

    def instance_method(self):
        return self

    @staticmethod
    def static_method():
        pass

    @test_decorator
    def decorated_func(self):
        pass


if __name__ == '__main__':
    the_class = TheClass()
    print('class_method type {type}'.format(type=type(TheClass.class_method)))  # method
    print('class_method type {type}'.format(type=type(the_class.class_method)))  # method
    print('instance_method type {type}'.format(type=type(the_class.instance_method)))  # method  通过类实例访问实例方法就是method
    print(TheClass.class_method)

    print('instance_method type {type}'.format(type=type(TheClass.instance_method)))  # function 通过类访问实例方法，就是function

    print('static_method type {type} '.format(type=type(the_class.static_method)))  # 静态方法无论如何访问都是 function
    print('static_method type {type} '.format(type=type(TheClass.static_method)))

    print('the_function type {type} '.format(type=type(the_function)))  # 对于一个函数，因为不会和任何类或实例绑定（除非使用MethodType将函数绑定到某个实例上)，必然不是方法。

    # 装饰器本身也是个函数
    print('test_decorator type {type} '.format(type=type(test_decorator)))
    print('decorated_func type {type} '.format(type=type(the_class.decorated_func)))

    # 如果类实现__call__方法
    # 执行结果True 其实例变为可调用对象
    print('class_instance callable {callable} '.format(callable=callable(the_class)))
    call_able=callable(the_class)

    # 实例的类型依旧是这个类，而不会变成函数或方法
    print('class_instance type {type} '.format(type=type(the_class)))

    print(TheClass.class_method())
    print(TheClass())  # _call__() 该方法的功能类似于在类中重载 () 运算符，使得类实例对象可以像调用普通函数那样，以“对象名()”的形式使用
