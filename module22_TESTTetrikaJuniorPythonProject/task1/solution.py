import inspect

def strict(func):
    def wrapper(*args, **kwargs):
        sig = inspect.signature(func)
        bound = sig.bind(*args, **kwargs)
        bound.apply_defaults()
        for name, value in bound.arguments.items():
            expected_type = func.__annotations__.get(name)
            if expected_type and not isinstance(value, expected_type):
                raise TypeError(
                    f"Argument '{name}' must be {expected_type.__name__}, got {type(value).__name__}"
                )
        return func(*args, **kwargs)
    return wrapper

@strict
def sum_two(a: int, b: int) -> int:
    return a + b
