import math

def strict(func):
    def wrapper(*args, **kwargs):
        annotations = func.__annotations__
        for (name, value), (arg_name, arg_type) in zip(zip(func.__code__.co_varnames, args), annotations.items()):
            if arg_name == 'return':
                continue
            if not isinstance(value, arg_type):
                raise TypeError(f"Argument '{arg_name}' must be {arg_type}, got {type(value)}")
        return func(*args, **kwargs)
    return wrapper

@strict
def circle_area(r: float) -> float:
    """Вычисляет площадь круга по радиусу r."""
    return math.pi * r * r

@strict
def triangle_area(a: float, b: float, c: float) -> float:
    """Вычисляет площадь треугольника по трем сторонам a, b, c (формула Герона)."""
    s = (a + b + c) / 2
    area = math.sqrt(s * (s - a) * (s - b) * (s - c))
    return area

def is_right_triangle(a: float, b: float, c: float) -> bool:
    """Проверяет, является ли треугольник с такими сторонами прямоугольным."""
    sides = sorted([a, b, c])
    return abs(sides[2]**2 - (sides[0]**2 + sides[1]**2)) < 1e-8
