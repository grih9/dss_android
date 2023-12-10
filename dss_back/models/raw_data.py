import logging
from typing import Union

from pydantic import BaseModel, model_validator
from pydantic import validator


class RawDataframe(BaseModel):
    """
    Модель данных для сырых, необработанных данных
    """
    variants: list[str]
    preferences: list[str]
    matrix: list[list[Union[int, float]]]
    choice_function: list[bool]
    weight_coefficients: list[float]

    @validator("weight_coefficients")
    def sum_coef_equal_to_one(cls, v):
        if sum(v) != 1.0:
            raise ValueError(f'Invalid sum {sum(v)}. 1.0 expected')
        return v

    @model_validator(mode="after")
    def validate_len_of_input(self):
        if len(self.weight_coefficients) != len(self.preferences):
            raise ValueError(f'Number of preferences doesn`t match number of weight coefficients')
        if len(self.choice_function) != len(self.preferences):
            raise ValueError(f'Number of preferences doesn`t match number of choice functions')
        if len(self.matrix) != len(self.variants):
            raise ValueError(f'Invalid size {len(self.matrix)} rows. {len(self.variants)} expected')
        for idx, row in enumerate(self.matrix):
            if len(row) != len(self.preferences):
                raise ValueError(f'Invalid size of {idx} row: {len(row)} columns. {len(self.preferences)} expected')
        return self


    # def __str__(self) -> str:
    #     table = PrettyTable()
    #     table.field_names = list(
    #         chain(["предпочтения", *self.matrix_col_names, "функция выбора", "весовые коэффициенты"]))
    #     table.align["предпочтения"] = "r"
    #     for matrix_row_name, matrix_row, choice, weight in zip(self.matrix_row_names, self.matrix, self.choice_function,
    #                                                            self.weight_coefficients):
    #         table.add_row(list(chain(
    #             [
    #                 matrix_row_name,
    #                 *matrix_row,
    #                 "Большее преобладает" if choice else "Меньшее преобладает",
    #                 weight
    #             ])))
    #     return str(table)
